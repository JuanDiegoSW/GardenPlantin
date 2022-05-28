package com.kirandroid.gardenmonitor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var name: String? = ""
    var emailID: String? = ""
    var password: String? = ""
    var confirmPwd: String? = ""

    lateinit var db: FirebaseFirestore
    private var db1 = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        AppPreferences.init(this)
        auth = Firebase.auth
        db = Firebase.firestore



        btnSubmit.setOnClickListener {

            name = editName.text.toString().trim()
            emailID = editEmailID.text.toString().trim()
            password = editPassword.text.toString().trim()
            confirmPwd = editConfirmPwd.text.toString().trim()

            if(name.isNullOrEmpty())
                Toast.makeText(this,"Please Enter Name!",Toast.LENGTH_LONG).show()
            else if (emailID.isNullOrEmpty())
                Toast.makeText(this, "Please enter Email ID",Toast.LENGTH_LONG).show()
            else if (password.isNullOrEmpty())
                //Toast.makeText(this, "Please enter Password",Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Password ${password.toString()}",Toast.LENGTH_LONG).show()
            else if (confirmPwd.isNullOrEmpty())
                //Toast.makeText(this, "Please re-enter your Password",Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Password ${confirmPwd.toString()}",Toast.LENGTH_LONG).show()
            else if (password.equals(confirmPwd)){
                /*
                auth.createUserWithEmailAndPassword(emailID!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }*/
                val userData = com.kirandroid.gardenmonitor.models.UserData(name!!,emailID!!, password!!)
                registerUser(name!!, emailID!!, password!!)


                /*
                db.collection("user_Data").document(emailID!!).set(
                    hashMapOf("name" to name)
                ).addOnSuccessListener{
                    AppPreferences.customerName = name
                    AppPreferences.customerEmail = emailID


                    Toast.makeText(this,"User Registered Successfully!",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userName",name)
                    startActivity(intent)
                }*/
                /*
                db.collection("User_Data")
                    .add(userData)
                    //.document(emailID!!)
                    .addOnSuccessListener {

                        // Setting Shared Preferences
                        AppPreferences.customerName = name
                        AppPreferences.customerEmail = emailID


                        Toast.makeText(this,"User Registered Successfully!",Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userName",name)
                        startActivity(intent)
                    }*/
            }


            else {
                Toast.makeText(this, "Password and Confirm Password should be same!",Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun registerUser(name: String, emailID: String, password: String) {
        auth.createUserWithEmailAndPassword(emailID,password).addOnCompleteListener(this) {task ->
            if(task.isSuccessful){
                val id = auth.currentUser!!.uid
                val map = HashMap<String, Any>()
                map["id"] = id
                map["name"] = name
                map["email"] = emailID
                map["password"] = password
                db1.collection("User_Data").document(id).set(map)
                    .addOnCompleteListener{ op ->
                        if(op.isSuccessful) {
                            Toast.makeText(this,"User Registered Successfully!",Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("userName",name)
                            startActivity(intent)
                        }else{
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}