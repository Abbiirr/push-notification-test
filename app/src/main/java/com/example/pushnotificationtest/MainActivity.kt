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

class MainActivity : ComponentActivity() {
    private var FCMtoken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FCMtoken = task.result
                    // Handle the FCM token as needed
                    FCMtoken?.let { token ->
                        println("FCM Token: $token")
                    }
                } else {
                    // Handle token retrieval failure
                }
                setContent {
                    PushNotificationTestTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Greeting("Android", FCMtoken ?: "Token not available")
                        }
                    }
                }
            }
    }

    @Composable
    fun Greeting(name: String, FCMtoken: String, modifier: Modifier = Modifier) {
        SelectionContainer {
            Text(
                text = "Hello $name!\n" +
                        "$FCMtoken",
                modifier = modifier
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PushNotificationTestTheme {
            Greeting("Android", "TestToken")
        }
    }
}
