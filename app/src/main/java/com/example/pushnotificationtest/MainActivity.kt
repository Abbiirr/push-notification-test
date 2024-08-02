package com.example.pushnotificationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pushnotificationtest.ui.theme.PushNotificationTestTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.installations.FirebaseInstallations


class MainActivity : ComponentActivity() {
    private var FCMtoken: String? = null
    private var instanceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Retrieve FCM Token
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FCMtoken = task.result
                    println("FCM Token: $FCMtoken")
                } else {
                    // Handle token retrieval failure
                    println("FCM Token retrieval failed")
                }
                setContentWithToken()
            }

        // Retrieve Instance ID
        FirebaseInstallations.getInstance().id
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    instanceId = task.result
                    println("Instance ID: $instanceId")
                } else {
                    // Handle ID retrieval failure
                    println("Instance ID retrieval failed")
                }
                setContentWithToken()
            }
    }

    private fun setContentWithToken() {
        if (FCMtoken != null && instanceId != null) {
            setContent {
                PushNotificationTestTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DisplayTokens("FCM Token: $FCMtoken\nInstance ID: $instanceId")
                    }
                }
            }
        }
    }

    @Composable
    fun DisplayTokens(tokenInfo: String, modifier: Modifier = Modifier) {
        SelectionContainer {
            Text(
                text = tokenInfo,
                modifier = modifier
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PushNotificationTestTheme {
            DisplayTokens("TestToken\nTestInstanceID")
        }
    }
}