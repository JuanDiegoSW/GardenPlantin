package com.kirandroid.gardenmonitor.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kirandroid.gardenmonitor.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        /*
        btnSendOTP.setOnClickListener {

            if(editPhoneNumber.text!!.length < 9) {
                Toast.makeText(this,"Please enter Valid Phone Number",Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("phoneNumber", editPhoneNumber.text.toString())
                startActivity(intent)
            }

        }*/
        LogginButton.setOnClickListener {
            if (EmailEditText.text.isNotBlank() && PasswordEditText.text.isNotEmpty()) {
                val email = EmailEditText.text.toString()
                val password = PasswordEditText.text.toString()
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed ${email},${password}.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

            }
        }
        sup.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }

    }
}