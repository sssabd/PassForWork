package com.example.passforwork.core.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passforwork.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registration)
    }
}