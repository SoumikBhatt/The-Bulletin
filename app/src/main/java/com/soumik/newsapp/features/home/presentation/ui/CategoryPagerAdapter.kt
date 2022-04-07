package com.soumik.newsapp.features.home.presentation.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
created by Soumik on 4/8/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class CategoryPagerAdapter(fragment:Fragment,private var categoryFragments : List<Fragment>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return categoryFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return categoryFragments[position]
    }
}