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

class Sign_In : AppCompatActivity() {

    // Declare variables at the class level
    private lateinit var email_id: EditText
    private lateinit var password: EditText
    private lateinit var Login: Button
    private lateinit var sign_up: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        email_id = findViewById(R.id.email_id)
        password = findViewById(R.id.password)
        Login = findViewById(R.id.Login)
        sign_up = findViewById(R.id.sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Login button click listener
        Login.setOnClickListener {
            val email = email_id.text.toString()
            val pass = password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            // Login success, navigate to MainActivity
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            // Display errors
                            email_id.error = "Invalid Email"
                            password.error = "Invalid Password"
                        }
                    }
            } else {
                if (email.isEmpty()) email_id.error = "Please enter email"
                if (pass.isEmpty()) password.error = "Please enter password"
            }
        }

        // Sign up button click listener
        sign_up.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    // Override onStart() method
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already signed in, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
