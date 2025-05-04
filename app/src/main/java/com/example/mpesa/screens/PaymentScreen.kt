package com.example.mpesa.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mpesa.daraja.DarajaViewModel
import com.example.mpesa.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onBackClick: () -> Unit,
    darajaViewModel: DarajaViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    var phone by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Make Payment") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number (254...)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (darajaViewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp))
            } else {
                Button(
                    onClick = {
                        darajaViewModel.pay(
                            DarajaViewModel.PaymentData(
                                phone = phone,
                                amount = amount,
                                passKey = settingsViewModel.passKey,
                                tillNumber = settingsViewModel.shortCode,
                                callbackUrl = settingsViewModel.callbackUrl,
                                consumerKey = settingsViewModel.consumerKey,
                                consumerSecret = settingsViewModel.consumerSecret,
                                environment = settingsViewModel.environment
                            )
                        )
                    }
                    ,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = phone.isNotEmpty() && amount.isNotEmpty()
                ) {
                    Text("Pay Now")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(darajaViewModel.resultMessage)
        }
    }
}