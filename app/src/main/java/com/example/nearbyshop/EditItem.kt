package com.example.nearbyshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_item.*

class EditItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        price.setText(intent.getStringExtra("price"))
        quantity.text=intent.getStringExtra("quantity")
        name.text=intent.getStringExtra("name")

        submit.setOnClickListener {
            var uid = FirebaseAuth.getInstance().uid
            var ref = FirebaseDatabase.getInstance().getReference("items/"+uid.toString()+"/"+name.text.toString())
            ref.child("price").setValue(price.text.toString())
                    startActivity(
                        Intent(this@EditItem,
                            Items::class.java)
                    )


        }
    }
}
