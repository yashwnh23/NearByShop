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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [items_fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [items_fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class items_fragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View?= inflater.inflate(R.layout.orders, container, false)
        val viewPager = view!!.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter=sliderAdapter(getChildFragmentManager())
        val tLayout = view?.findViewById<TabLayout>(R.id.tLayout)
        tLayout.setupWithViewPager(viewPager)
        return view
    }
    private inner class sliderAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        internal val tabs = arrayOf("Add Items", "Items List")

        override fun getItem(position: Int): Fragment {
            when(position){
                0->return items_frag()
                1->return items_List()
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
