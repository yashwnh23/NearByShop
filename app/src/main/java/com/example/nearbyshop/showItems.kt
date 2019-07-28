package com.example.nearbyshop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_show_items.*

class showItems : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_show_items, container, false)
        val acti = activity as NearByShop
        val uid = acti!!.getUids()
        val grocery = view.findViewById<LinearLayout>(R.id.grocery)
        grocery.setOnClickListener {
            replace(grocerytv.text.toString(),uid)

        }
        val beverages = view.findViewById<LinearLayout>(R.id.beverage)
        beverages.setOnClickListener {
            replace(beveragetv.text.toString(),uid)
        }
        val rice = view.findViewById<LinearLayout>(R.id.rice)
        val flours = view.findViewById<LinearLayout>(R.id.flours)
        val vegetables = view.findViewById<LinearLayout>(R.id.vegetables)
        val milkpro = view.findViewById<LinearLayout>(R.id.milkproducts)
        val masala = view.findViewById<LinearLayout>(R.id.masala)
        val snacks = view.findViewById<LinearLayout>(R.id.chips)
        val choco = view.findViewById<LinearLayout>(R.id.chocolates)
        rice.setOnClickListener {
            replace(ricetv.text.toString(),uid)
        }
        flours.setOnClickListener {
            replace(flourstv.text.toString(),uid)
        }
        vegetables.setOnClickListener {
            replace(vegetablestv.text.toString(),uid)
        }
        milkpro.setOnClickListener {
            replace(milkProductstv.text.toString(),uid)
        }
        masala.setOnClickListener {
            replace(masalatv.text.toString(),uid)
        }
        snacks.setOnClickListener {
            replace(chipstv.text.toString(),uid)
        }
        choco.setOnClickListener {
            replace(chocolatestv.text.toString(),uid)
        }
        return view
    }

    fun replace(a:String,b:String){
        val intent = Intent(this.context,SearchItems::class.java)
        intent.putExtra("uid",b)
        intent.putExtra("type",a)
        startActivity(
            intent
        )
    }


}
