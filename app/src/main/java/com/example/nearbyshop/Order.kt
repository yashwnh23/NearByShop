package com.example.nearbyshop

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_order.toolbar

class Order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)




        // Set up the ViewPager with the sections adapter.


        nav.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when(p0.itemId){
                    R.id.orders -> {
                        val ft = supportFragmentManager.beginTransaction()
                        ft.replace(R.id.frag,Orders())
                        ft.commit()
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    R.id.ordersList -> {
                        val ft = supportFragmentManager.beginTransaction()
                        ft.replace(R.id.frag,items_fragment())
                        ft.commit()
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    R.id.signOut->{
                        FirebaseAuth.getInstance().signOut()
                        startActivity(
                            Intent(this@Order,MainActivity::class.java)
                        )
                    }
                    else->{
                        val ft = supportFragmentManager.beginTransaction()
                        ft.replace(R.id.frag,items_fragment())
                        ft.commit()
                        drawer.closeDrawer(GravityCompat.START);
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
