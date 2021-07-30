package com.assignment.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.assignment.BR
import com.assignment.R
import com.assignment.databinding.MainFragmentBinding
import com.assignment.ui.base.BaseFragment
import com.assignment.ui.utils.ItemDivider

class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>(R.layout.main_fragment) {

    override fun provideViewModelClass() = MainViewModel::class.java

    override val bindingVariable: Int = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchNews(true)

        viewModel.getItemClick().observe(this, {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(it.url, it.title)
            navController.navigate(action)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.rvNewsItems.addItemDecoration(
            ItemDivider(
                resources.getDimensionPixelSize(R.dimen.dimen_8dp)
            )
        )
        setToolbarTitle(getString(R.string.title_main))
    }
}