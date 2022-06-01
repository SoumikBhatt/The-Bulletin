package com.soumik.newsapp.features.settings.presenter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soumik.newsapp.BuildConfig
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.databinding.SettingsFragmentBinding
import com.soumik.newsapp.features.settings.presenter.viewmodel.SettingsViewModel
import javax.inject.Inject

class SettingsFragment : Fragment() {

    companion object {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var mBinding : SettingsFragmentBinding
    @Inject lateinit var mViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = SettingsFragmentBinding.inflate(inflater)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as NewsApp).appComponent.inject(this)

        init()
        setViews()
        setUpObservers()
        setViewListeners()

    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        mBinding.apply {
            tvMenuVersionDesc.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        }
    }

    private fun init() {
        mViewModel.apply { checkAppTheme(requireContext().resources) }
    }

    private fun setUpObservers() {
        mViewModel.apply {
            darkThemeEnabled.observe(viewLifecycleOwner){
                if (it) {
                    mBinding.apply {
                        tvMenuTheme.text = getString(R.string.light_mode)
                        tvMenuThemeDesc.text = getString(R.string.switch_app_theme_to_light_mode)
                        tvMenuTheme.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_light_mode,0)
                    }
                } else {
                    mBinding.apply {
                        tvMenuTheme.text = getString(R.string.dark_mode)
                        tvMenuThemeDesc.text = getString(R.string.switch_app_theme_to_dark_mode)
                        tvMenuTheme.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_dark_mode,0)
                    }
                }
            }
        }
    }

    private fun setViewListeners() {
        mBinding.apply {
            tvMenuTheme.setOnClickListener { mViewModel.switchAppTheme(requireContext().resources) }
            tvMenuThemeDesc.setOnClickListener { mViewModel.switchAppTheme(requireContext().resources) }
        }
    }

}