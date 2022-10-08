package com.yusufcancakmak.chattapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener {
            val email=email_editext_login.text.toString()
            val password=password_edittex_login.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful){

            }
        }.addOnFailureListener {

        }
        }
        back_to_register_text.setOnClickListener {
            finish()
        }
    }
}