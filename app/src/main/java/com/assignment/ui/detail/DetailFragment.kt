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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.setUrl(DetailFragmentArgs.fromBundle(it).url)
        }
    }
}