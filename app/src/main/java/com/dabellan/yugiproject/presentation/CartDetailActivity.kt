package com.dabellan.yugiproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class CartDetailActivity : ComponentActivity() {

    private lateinit var cartId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartId = intent.getStringExtra("cartId").toString()
        setContent {
            Content()
        }

    }


    @Composable
    fun Content() {
        Column {
            Text(text = cartId)
        }
    }
}