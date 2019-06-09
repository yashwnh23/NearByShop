package com.example.nearbyshop

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_order.toolbar

class Order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)

        tLayout.addTab(tLayout.newTab().setText("Orders"))
        tLayout.addTab(tLayout.newTab().setText("Recent Order"))

        nav.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when(p0.itemId){
                    R.id.orders -> {
                        startActivity(
                            Intent(
                                this@Order,
                                ItemsList::class.java
                            )
                        )
                    }
                    R.id.ordersList -> {
                        startActivity(
                            Intent(
                                this@Order,
                                Items::class.java
                            )
                        )
                    }

                }
                return true
            }

        })

        val tog = ActionBarDrawerToggle(this@Order, drawer,toolbar,R.string.open_nav,R.string.close_nav)
        drawer.addDrawerListener(tog)
        tog.syncState()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

}
