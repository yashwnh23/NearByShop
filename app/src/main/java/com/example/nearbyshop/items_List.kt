package com.example.nearbyshop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_items__list.itemView
import kotlinx.android.synthetic.main.fragment_items__list.*
import kotlinx.android.synthetic.main.itemsindiview.view.*


class items_List : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_items__list, container, false)
        val context = this.context
        val list1 = arrayOf("Grocery","Snacks","Masalas","Flours","Rices", "Vegetables", "Beverages" , "Choclates","Milk Products")
        val dataAdapter = ArrayAdapter<String>(
            this.context,
            android.R.layout.simple_spinner_item, list1
        )
        val spinner=view!!.findViewById<Spinner>(R.id.spinner)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dataAdapter)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var list = mutableListOf<Item>()

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
                                    var inflater = LayoutInflater.from(context)
                                    var v = inflater.inflate(R.layout.itemsindiview, null)
                                    v.name.text = list.get(position).name
                                    v.price.text = list.get(position).price
                                    v.quantity.text = list.get(position).quantity
                                    v.edit.text = "Edit"
                                    v.edit.setOnClickListener {
                                        val intent = Intent(context, EditItem::class.java)
                                        intent.putExtra("name", list.get(position).name)
                                        intent.putExtra("price", list.get(position).price)
                                        intent.putExtra("quantity", list.get(position).quantity)

                                        startActivity(intent)
                                    }
                                    v.remove.setOnClickListener {
                                        val selected = spinner.selectedItem.toString()
                                        var uid = FirebaseAuth.getInstance().uid.toString()
                                        var ref = FirebaseDatabase.getInstance()
                                            .getReference("items/" + uid + "/" + selected + "/" + list.get(position).name)
                                        ref.removeValue().addOnCompleteListener {
                                            startActivity(
                                                Intent(context,
                                                    Order::class.java)
                                            )
                                        }
                                    }
                                    Glide.with(context!!).load(list.get(position).url).into(v.img)


                                    return v
                                }


                            }

                        }
                    })

            }


        }
        return view
    }

}
