package com.example.LRPD

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.LRPD.navigation.NavGraph
import com.example.LRPD.ui.theme.Lrp2Theme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        Log.d("FIREBASE_TEST", "Firebase iniciado correctamente")

        setContent {
            Lrp2Theme {
                NavGraph()
            }
        }
    }
}