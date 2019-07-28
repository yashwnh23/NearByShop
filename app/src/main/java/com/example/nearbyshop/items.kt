package com.example.nearbyshop

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class items : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_items, container, false)
        val viewPager = view!!.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter=sliderAdapter(getChildFragmentManager())
        val tLayout = view?.findViewById<TabLayout>(R.id.tabLayout)
        tLayout.setupWithViewPager(viewPager)
        return view
    }
    private inner class sliderAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        internal val tabs = arrayOf("Add Items", "Items List")

        override fun getItem(position: Int): Fragment {
            when(position){
                0->return items_frag()
                1->return showItems()
                else->return RecentOrder()
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabs[position]
        }
    }
}
