package com.example.mpesa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MpesaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DarajaScreen()
                }
            }
        }
    }
}

@Composable
fun DarajaScreen(
    viewModel: DarajaViewModel = viewModel()  // Fixed: Added parentheses
) {
    var phone by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number (254...)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Button(
                onClick = { viewModel.pay(phone, amount) },
                modifier = Modifier.fillMaxWidth(),
                enabled = phone.isNotEmpty() && amount.isNotEmpty()
            ) {
                Text("Pay Now")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Response: ${viewModel.resultMessage}")
    }
}

@Composable
fun MpesaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}