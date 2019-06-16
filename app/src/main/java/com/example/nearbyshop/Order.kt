package com.example.nearbyshop

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_order.toolbar

class Order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)


        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

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
                    else->{}

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




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
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
