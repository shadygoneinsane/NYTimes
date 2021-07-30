package com.assignment.ui.detail

import android.os.Bundle
import android.view.View
import com.assignment.BR
import com.assignment.R
import com.assignment.databinding.DetailFragmentBinding
import com.assignment.ui.base.BaseFragment

class DetailFragment :
    BaseFragment<DetailFragmentBinding, DetailViewModel>(R.layout.detail_fragment) {

    override fun provideViewModelClass() = DetailViewModel::class.java

    override val bindingVariable: Int = BR.viewModel

    private val url: String? by lazy {
        arguments?.let { DetailFragmentArgs.fromBundle(it).url }
    }

    private val title: String? by lazy {
        arguments?.let { DetailFragmentArgs.fromBundle(it).title }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url?.let {
            viewModel.showLoading(true)
            viewModel.setUrl(it)
        } ?: showErrorDialog(getString(R.string.something_went_wrong))

        setToolbarTitle(title ?: getString(R.string.title_main))
    }
}