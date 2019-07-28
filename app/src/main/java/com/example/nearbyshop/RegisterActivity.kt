package com.example.nearbyshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val user = FirebaseAuth.getInstance().currentUser
        var fAuth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            fAuth.createUserWithEmailAndPassword(
                email.text.toString(), password.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful){
            var dBase = FirebaseDatabase.getInstance()
            var dRef = dBase.getReference("users")
            var uid = FirebaseAuth.getInstance().uid
            var child_dRef = dRef.child(uid.toString())
            child_dRef.child("email").
                setValue(email.text.toString())
            child_dRef.child("password").
                setValue(password.text.toString())
            child_dRef.child("mno").
                setValue(mobile.text.toString())
            child_dRef.child("name").
                setValue(name.text.toString())
            child_dRef.child("city").
                setValue(city.text.toString())
            child_dRef.child("fcm_token").
                setValue(FirebaseInstanceId.getInstance().token)
            child_dRef.child("uid").
                 setValue(FirebaseAuth.getInstance().uid.toString())

            startActivity(
                Intent(this@RegisterActivity,
               Order::class.java)
            )
                }
                else{
                    Toast.makeText(this@RegisterActivity,"Registration Failed",
                        Toast.LENGTH_LONG).show()

        }
            }
        }

    }
}
