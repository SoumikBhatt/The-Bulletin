package com.soumik.newsapp.features.details.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.soumik.newsapp.core.utils.DateUtils
import com.soumik.newsapp.databinding.NewsDetailsFragmentBinding
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {

//    @Inject
//    lateinit var mViewModel: NewsDetailsViewModel
    private lateinit var mBinding: NewsDetailsFragmentBinding
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = NewsDetailsFragmentBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        (requireActivity().application as NewsApp).appComponent.inject(this)

        setUpViews()

    }

    private fun setUpViews() {
        val article = args.article
        mBinding.apply {
            Picasso.get().load(article?.urlToImage).into(ivNewsImage)
            tvNewsTitle.text = HtmlCompat.fromHtml(article?.title?:"",HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsAuthor.text = HtmlCompat.fromHtml(article?.author?:"",HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsTime.text = DateUtils.provideFormattedDate("yyyy-MM-dd'T'HH:mm:ss'Z'",article?.publishedAt)
            tvNewsContent.text = HtmlCompat.fromHtml(article?.content?:"",HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

}