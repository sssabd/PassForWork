package com.example.passforwork.features.registration.presentation

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.passforwork.R
import com.example.passforwork.databinding.ObjectListDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ObjectListDialogFragment(
    val items: List<String>,//TODO сюда будет передаваться список объектов
    private val onDestroy: () -> Unit
) : BottomSheetDialogFragment() {

    lateinit var binding: ObjectListDialogFragmentBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet: FrameLayout =
                dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                    ?: return@setOnShowListener
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            if (bottomSheet.layoutParams != null) {
                showFullScreenBottomSheet(bottomSheet)
            }
            bottomSheet.setBackgroundResource(android.R.color.transparent)
            expandBottomSheet(bottomSheetBehavior)
        }

        return bottomSheetDialog
    }

    private fun showFullScreenBottomSheet(bottomSheet: FrameLayout) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheet.layoutParams = layoutParams
    }

    private fun expandBottomSheet(bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ObjectListDialogFragmentBinding.bind(
            inflater.inflate(
                R.layout.object_list_dialog_fragment,
                container
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        TODO в адаптере через лямбду нужно будет сделать колбэк, при нажатие на элемент закрываем диалог и возращаем id объекта

        binding.exitButton.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN }
    }

    override fun onDestroy() {
        onDestroy.invoke()
        super.onDestroy()
    }

}