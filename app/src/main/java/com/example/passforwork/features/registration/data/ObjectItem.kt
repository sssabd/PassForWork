package com.example.passforwork.features.registration.data

data class ObjectItem(
    val objectId: Int,
    val objectString: String,
    val onClick: () -> Unit
)
