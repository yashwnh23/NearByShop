package com.example.nearbyshop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.itemsindiview.view.*

class Items : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        val firebase = FirebaseDatabase.getInstance().getReference("items")
        firebase.addValueEventListener(
        object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

             override fun onDataChange(p0: DataSnapshot) {

                 var items = p0.children
                 var list = mutableListOf<Item>()

                 items.forEach{
                     var item = Item( )
                     var childOfItems = it.children
                     childOfItems.forEach{
                         when(it.key){
                             "name" -> {item.name  = it.value.toString() }
                             "price" -> {item.price  = it.value.toString() }
                             "quantity" -> { item.quantity = it.value.toString()}
                             "profile_pic_url" -> {item.profile_pic_url  = it.value.toString() }


                             }
                     }
                     list.add(item)
                 }
                 itemView.adapter = object : BaseAdapter() {

                     override fun getCount(): Int = list.size

                     override fun getItem(position: Int): Any = 0

                     override fun getItemId(position: Int): Long = 0

                     override fun getView(position: Int, convertView: View?,
                                          parent: ViewGroup?): View {
                         var inflater = LayoutInflater.from(this@Items)
                         var v = inflater.inflate(R.layout.itemsindiview,null)
                         v.name.text = list.get(position).name
                         v.price.text = list.get(position).price
                         v.quantity.text = list.get(position).quantity
                         Glide.with(this@Items).
                             load(list.get(position).profile_pic_url).
                             into(v.img)


                        return  v
                     }


                 }
            }
        })
    }
}