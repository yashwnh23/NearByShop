package com.example.nearbyshop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.orders.*
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.google.firebase.auth.FirebaseAuth


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Orders.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Orders.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Orders : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view:View?= inflater.inflate(R.layout.orders, container, false)
        val viewPager = view!!.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter=sliderAdapter(getChildFragmentManager())
        val tLayout = view?.findViewById<TabLayout>(R.id.tLayout)
        tLayout.setupWithViewPager(viewPager)
        return view
    }
    private inner class sliderAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        internal val tabs = arrayOf("tab1", "tab2")

        override fun getItem(position: Int): Fragment {
            when(position){
                0->return RecentOrder()
                1->return RecentOrder()
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
