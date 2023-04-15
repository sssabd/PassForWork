package com.example.passforwork.features.registration.presentation

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.passforwork.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val PHONE_LENGTH = 14

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        setListenersToEditTexts()

        return binding.root
    }

    private fun setListenersToEditTexts() {
        with(binding) {
            nameEditText.doAfterTextChanged { checkSignUpButton() }
            surnameEditText.doAfterTextChanged { checkSignUpButton() }
            postEditText.doAfterTextChanged { checkSignUpButton() }
            phoneEditText.doAfterTextChanged {
                setPhoneError(it!!)
                checkSignUpButton()
            }
        }
    }
    private fun setPhoneError(phone: Editable) {
        if(phone.length!=PHONE_LENGTH) {
            binding.phoneEditText.error = "Некорректный телефон!"
        } else {
            binding.phoneEditText.error = null
        }
    }
    private fun checkSignUpButton() {
        binding.signUpButton.isEnabled = necessaryFieldsWereEntered() &&
                binding.phoneEditText.error == null
    }
    private fun necessaryFieldsWereEntered(): Boolean {
        with(binding) {
            return nameEditText.text!!.isNotBlank() && surnameEditText.text!!.isNotBlank()
                    && postEditText.text!!.isNotBlank() && phoneEditText.text!!.isNotBlank()
        }
    }

}