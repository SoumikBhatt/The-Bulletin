package com.soumik.newsapp.features.home.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soumik.newsapp.databinding.HomeFragmentBinding
import com.soumik.newsapp.features.home.presenter.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var mViewModel: HomeViewModel
    private lateinit var mBinding : HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = HomeFragmentBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        setViews()
        setObservers()
    }

    private fun setObservers() {
        mViewModel.apply {
            newsData.observe(viewLifecycleOwner) {
                if (it.status=="ok") {

                }
            }
        }
    }

    private fun setViews() {
    }

    private fun init() {
    }

}