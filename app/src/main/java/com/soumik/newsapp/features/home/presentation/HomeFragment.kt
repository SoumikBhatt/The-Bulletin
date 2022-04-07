package com.soumik.newsapp.features.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.soumik.newsapp.R
import com.soumik.newsapp.databinding.HomeFragmentBinding
import com.soumik.newsapp.features.home.presentation.ui.CategoryPagerAdapter
import com.soumik.newsapp.features.home.presentation.ui.NewsFeedFragment

class HomeFragment : Fragment() {

    private lateinit var mBinding: HomeFragmentBinding
    private lateinit var mCategoryPagerAdapter: CategoryPagerAdapter
    private val categoryFragmentList: List<Fragment> by lazy {
        listOf(
            NewsFeedFragment.newInstance(""),
            NewsFeedFragment.newInstance(mCategoriesValue[1]),
            NewsFeedFragment.newInstance(mCategoriesValue[2]),
            NewsFeedFragment.newInstance(mCategoriesValue[3]),
            NewsFeedFragment.newInstance(mCategoriesValue[4]),
            NewsFeedFragment.newInstance(mCategoriesValue[5]),
            NewsFeedFragment.newInstance(mCategoriesValue[6])
        )
    }
    private val mCategoriesTab : List<String> by lazy {
        listOf(resources.getString(R.string.tab_all),resources.getString(R.string.tab_business),resources.getString(R.string.tab_entertainment),
            resources.getString(R.string.tab_sports),resources.getString(R.string.tab_health),resources.getString(R.string.tab_science),resources.getString(R.string.tab_technology))
    }
    private val mCategoriesValue : List<String> by lazy {
        listOf("","business","entertainment", "sports","health","science","technology")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = HomeFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        mCategoryPagerAdapter = CategoryPagerAdapter(this,categoryFragmentList)
        mBinding.newsViewPager.adapter = mCategoryPagerAdapter
        TabLayoutMediator(mBinding.tabCategories,mBinding.newsViewPager) {tab,position ->
            tab.text = mCategoriesTab[position]
        }.attach()
    }

}