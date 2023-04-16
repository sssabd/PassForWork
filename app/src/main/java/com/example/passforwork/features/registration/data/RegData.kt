package com.example.passforwork.features.registration.data

import com.google.gson.annotations.SerializedName

class RegData (
    @SerializedName("full_name") val fullName: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("object_id") val objectId: Int,
    @SerializedName("position") val position: String,
)