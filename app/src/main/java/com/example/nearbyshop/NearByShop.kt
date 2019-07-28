package com.example.nearbyshop

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_near_by_shop.*
import kotlinx.android.synthetic.main.activity_order.*

class NearByShop : AppCompatActivity() {
    var uid:String="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_by_shop)
        setSupportActionBar(usertoolbar)

        navigation.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {

                when(p0.itemId){
                    R.id.itemMenu -> {

                        fragm(showItems())
                        drawer1.closeDrawer(GravityCompat.START);
                    }
                    R.id.orders -> {
                        fragm(Orders())
                        drawer1.closeDrawer(GravityCompat.START);
                    }
                    R.id.cart -> {
                        fragm(items_fragment())
                        drawer1.closeDrawer(GravityCompat.START);
                    }
                    R.id.signOut->{
                        FirebaseAuth.getInstance().signOut()
                        startActivity(
                            Intent(this@NearByShop,MainActivity::class.java)
                        )
                    }
                    else->{}

                }

                return true

            }


        })//
        uid = intent.getStringExtra("uid")
        Toast.makeText(this,uid,Toast.LENGTH_LONG).show()
        val tog = ActionBarDrawerToggle(this@NearByShop, drawer1,usertoolbar,R.string.open_nav,R.string.close_nav)
        drawer1.addDrawerListener(tog)
        tog.syncState()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.container,showItems())
        ft.commit()
        drawer1.closeDrawer(GravityCompat.START);

    }//onCreate

    fun getUids():String{
        return uid
    }
    fun fragm(fr:Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container,fr)
        ft.commit()
        drawer1.closeDrawer(GravityCompat.START);
    }

    override fun onBackPressed() {
        if(drawer1.isDrawerOpen(GravityCompat.START)){
            drawer1.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when(position)
            {
                0 -> return Orders()
                1 -> return RecentOrder()
                else-> return  Orders()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }
    }
}
