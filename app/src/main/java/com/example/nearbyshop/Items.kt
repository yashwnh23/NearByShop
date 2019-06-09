package com.example.nearbyshop

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.itemsindiview.view.*

class Items : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        var list = mutableListOf<Item>()
        val list1 = arrayOf("list", "Grocery", "Beverages" , "Choclates")
        val dataAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, list1
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dataAdapter)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Items, spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
                if (position>0) {
                    val selected = spinner.selectedItem.toString()
                    val firebase = FirebaseDatabase.getInstance()
                        .getReference("items/" + FirebaseAuth.getInstance().uid.toString() + "/" + selected)
                    firebase.addValueEventListener(
                        object : ValueEventListener {

                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {

                                val items = p0.children

                                items.forEach {
                                    val item = Item()
                                    val childOfItems = it.children
                                    childOfItems.forEach {
                                        when (it.key) {
                                            "name" -> {
                                                item.name = it.value.toString()
                                            }
                                            "price" -> {
                                                item.price = it.value.toString()
                                            }
                                            "quantity" -> {
                                                item.quantity = it.value.toString()
                                            }
                                            "url" -> {
                                                item.url = it.value.toString()
                                            }
                                            "" -> {
                                            }
                                        }
                                    }
                                    list.add(item)
                                }
                                itemView.adapter = object : BaseAdapter() {

                                    override fun getCount(): Int = list.size

                                    override fun getItem(position: Int): Any = 0

                                    override fun getItemId(position: Int): Long = 0

                                    override fun getView(
                                        position: Int, convertView: View?,
                                        parent: ViewGroup?
                                    ): View {
                                        var inflater = LayoutInflater.from(this@Items)
                                        var v = inflater.inflate(R.layout.itemsindiview, null)
                                        v.name.text = list.get(position).name
                                        v.price.text = list.get(position).price
                                        v.quantity.text = list.get(position).quantity
                                        v.edit.text = "Edit"
                                        v.edit.setOnClickListener {
                                            val intent = Intent(this@Items, EditItem::class.java)
                                            intent.putExtra("name", list.get(position).name)
                                            intent.putExtra("price", list.get(position).price)
                                            intent.putExtra("quantity", list.get(position).quantity)

                                            startActivity(intent)
                                        }
                                        Glide.with(this@Items).load(list.get(position).url).into(v.img)


                                        return v
                                    }


                                }

                            }
                        })
                }
            }


        }
        items.setOnClickListener {
            startActivity(
                Intent(this@Items,
                    ItemsList::class.java)
            )
        }
        ord.setOnClickListener {
            startActivity(
                Intent(this@Items,
                    Order::class.java)
            )
        }
    }
}
