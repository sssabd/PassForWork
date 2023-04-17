package com.example.passforwork.features.registration.presentation.objectList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.passforwork.R
import com.example.passforwork.databinding.ItemObjectBinding
import com.example.passforwork.databinding.ObjectListDialogFragmentBinding
import com.example.passforwork.features.registration.presentation.RegistrationViewModel

class ObjectListAdapter(
    val objectList: List<RegistrationViewModel.ObjectItem>,
    val onClick: () -> Unit
) : RecyclerView.Adapter<ObjectListAdapter.ObjectListViewHolder>() {

    class ObjectListViewHolder(val binding: ItemObjectBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectListViewHolder {
        val binding = ItemObjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ObjectListViewHolder(binding)
    }

    override fun getItemCount(): Int = objectList.size

    override fun onBindViewHolder(holder: ObjectListViewHolder, position: Int) {
        val objectElement = objectList[position]
        holder.binding.objectNameTextView.text = objectElement.objectString
        holder.binding.root.setOnClickListener {
            objectElement.onClick.invoke()
            onClick.invoke()
        }
    }
}