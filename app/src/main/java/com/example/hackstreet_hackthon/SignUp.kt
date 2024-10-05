package com.example.hackstreet_hackthon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    // Declare variables at the class level
    private lateinit var email_id: EditText
    private lateinit var password: EditText
    private lateinit var repeat_password: EditText
    private lateinit var sign_up: Button
    private lateinit var sign_in: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        email_id = findViewById(R.id.email_id)
        password = findViewById(R.id.password)
        repeat_password = findViewById(R.id.repeat_password)
        sign_up = findViewById(R.id.sign_up)
        sign_in = findViewById(R.id.sign_in)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Sign up button click listener
        sign_up.setOnClickListener {
            val email = email_id.text.toString()
            val pass = password.text.toString()
            val repass = repeat_password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && repass.isNotEmpty()) {
                if (pass == repass) {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                } else {
                    repeat_password.error = "Password does not match"
                }
            } else {
                if (email.isEmpty()) email_id.error = "Please enter email"
                if (pass.isEmpty()) password.error = "Please enter password"
            }
        }

        // Sign in button click listener
        sign_in.setOnClickListener {
            startActivity(Intent(this, sign_in::class.java))
        }
    }

    // Override the onStart() method outside of onCreate()
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
