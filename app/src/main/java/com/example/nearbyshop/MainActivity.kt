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
                   var ref = FirebaseDatabase.getInstance().getReference("users")
                      .orderByChild("email").equalTo(email.text.toString())
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            var snap=p0.children
                                snap.forEach {
                                   if(it.child("user").value.toString() == "admin") {
                                       startActivity(
                                           Intent(
                                               this@MainActivity,
                                               ItemsList::class.java
                                           )
                                       )
                                   }
                                   else{
                                       startActivity(
                                           Intent(
                                               this@MainActivity,
                                               Items::class.java
                                           )
                                       )
                                   }

                                }
                            }
                    })


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
