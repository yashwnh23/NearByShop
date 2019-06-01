package com.example.nearbyshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_items_list.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var fAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fAuth = FirebaseAuth.getInstance()
        login.setOnClickListener {
            fAuth!!.signInWithEmailAndPassword(
                email.text.toString(), password.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            ItemsList::class.java
                        )
                    )
                }
                else
                    Toast.makeText(this@MainActivity,"Login Failed",Toast.LENGTH_LONG).show()
            }
        }
        reg.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    RegisterActivity::class.java
                )
            )
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = fAuth!!.getCurrentUser()
        if (currentUser != null) {
            startActivity(
                Intent(
                    this@MainActivity,
                    Items::class.java
                )
            )
        }
    }

}
