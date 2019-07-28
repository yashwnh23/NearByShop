package com.example.nearbyshop

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_shops_list.*
import kotlinx.android.synthetic.main.content_shops_list.view.*

class ShopsList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shops_list)
        setSupportActionBar(toolbar)
        var ref = FirebaseDatabase.getInstance().getReference("users").orderByChild("user").equalTo("admin")
        ref.addValueEventListener(object :ValueEventListener {
            var list = mutableListOf<Item>()
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                var snap=p0.children
                var l = mutableListOf<String>()
                var j=mutableListOf<String>()
                var k=mutableListOf<String>()
                snap.forEach {
                   j .add( it.child("name").value.toString())
                   k .add( it.child("uid").value.toString())
                   l .add(it.child("email").value.toString() )
                    }
                itemView.adapter = object : BaseAdapter() {

                    override fun getCount(): Int = l.size

                    override fun getItem(position: Int): Any = 0

                    override fun getItemId(position: Int): Long = 0

                    override fun getView(
                        position: Int, convertView: View?,
                        parent: ViewGroup?
                    ): View {
                        var inflater = LayoutInflater.from(this@ShopsList)
                        var v = inflater.inflate(R.layout.content_shops_list, null)


                        v.shopname.text = j.get(position)
                        v.mail.text = l.get(position)
                        v.shopname.setOnClickListener {
                            val intent = Intent(this@ShopsList, NearByShop::class.java)
                            intent.putExtra("uid", k.get(position))

                            startActivity(intent)
                        }
                        return v
                    }

                }
            }

        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
