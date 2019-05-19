package com.example.nearbyshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fAuth = FirebaseAuth.getInstance()
        login.setOnClickListener {
            fAuth.signInWithEmailAndPassword(
                email.text.toString(), password.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful)
                    startActivity(Intent(this@MainActivity,
                        ItemsList::class.java))

                else
                    Toast.makeText(this@MainActivity,"Auth Failed",
                        Toast.LENGTH_LONG).show()
            }
        }

        reg.setOnClickListener{
            startActivity(Intent(this@MainActivity,
                RegisterActivity::class.java))
        }



    }

}