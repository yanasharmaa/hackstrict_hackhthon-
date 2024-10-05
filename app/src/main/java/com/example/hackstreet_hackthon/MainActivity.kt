package com.example.hackstreet_hackthon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    lateinit var textview : TextView
    lateinit var editText : EditText
    lateinit var button : Button
    lateinit var logout:Button
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        textview = findViewById(R.id.textview)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        auth = Firebase.auth
        logout = findViewById(R.id.logout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button.setOnClickListener{
            val input = editText.text.toString()
        }
        logout.setOnClickListener{
            Firebase.auth.signOut()
            startActivity(Intent(this,Sign_In::class.java))
        }

    }
}