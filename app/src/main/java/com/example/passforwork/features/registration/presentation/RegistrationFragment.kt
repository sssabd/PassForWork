package com.example.passforwork.features.registration.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.passforwork.core.base.view.PhoneTextWatcher
import com.example.passforwork.core.base.view.observeWithLifecycle
import com.example.passforwork.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModel<RegistrationViewModel>()
    private val PHONE_LENGTH = 14


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.phoneEditText.setText("+7 (")
        binding.phoneEditText.addTextChangedListener(PhoneTextWatcher("+* (###) ###-##-##"))

        binding.signUpButton.setOnClickListener {
            viewModel.validateRegData(
                name = binding.nameEditText.text?.toString()?.trim(),
                surname = binding.surnameEditText.text?.toString()?.trim(),
                patronymic = binding.patronymicEditText.text?.toString()?.trim(),
                phoneNumber = binding.phoneEditText.text?.toString(),
                objectId = 1 //TODO
            )
        }

        viewModel.fieldNameValidationState.observeWithLifecycle(this) {
            setObserver(it, binding.nameEditText, binding.nameInputLayout)
        }
        viewModel.fieldNumberValidationState.observeWithLifecycle(this) {
            setObserver(it, binding.phoneEditText, binding.phoneInputLayout)
        }
        viewModel.fieldPatronymicValidationState.observeWithLifecycle(this) {
            setObserver(it, binding.patronymicEditText, binding.patronymicInputLayout)
        }
        viewModel.fieldSurnameValidationState.observeWithLifecycle(this) {
            setObserver(it, binding.surnameEditText, binding.surnameInputLayout)
        }

        binding.nameEditText.doAfterTextChanged { binding.nameInputLayout.error = null }
        binding.surnameEditText.doAfterTextChanged { binding.surnameInputLayout.error = null }
        binding.patronymicEditText.doAfterTextChanged { binding.patronymicInputLayout.error = null }
        binding.phoneEditText.doAfterTextChanged { binding.phoneInputLayout.error = null }

        var isObjectListDialogOpen = false
        binding.selectObject.setOnClickListener {
            if (!isObjectListDialogOpen) {
                val dialog = ObjectListDialogFragment(
                    emptyList()//TODO
                ) {
                    isObjectListDialogOpen = false
                }
                isObjectListDialogOpen = true
                dialog.show(
                    parentFragmentManager,
                    "ObjectListDialogFragment"
                )
            }
        }
    }

    private fun setObserver(
        it: RegistrationViewModel.ValidationState,
        view: TextInputEditText,
        layout: TextInputLayout
    ) {
        when (it) {
            RegistrationViewModel.ValidationState.Empty -> {
                layout.error = null
            }
            is RegistrationViewModel.ValidationState.Error -> {
                layout.error = it.errorText
                showSoftKeyboard(view, layout)
            }
            RegistrationViewModel.ValidationState.Success -> {
                layout.error = null
            }
        }
    }

    private fun showSoftKeyboard(view: TextInputEditText, layout: View) {
        layout.parent.requestChildFocus(layout, layout)
        if (view.requestFocus()) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            view.setSelection(view.length())
        }
    }

}