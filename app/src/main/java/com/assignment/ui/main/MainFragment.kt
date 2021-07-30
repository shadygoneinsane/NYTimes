package com.assignment.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.assignment.BR
import com.assignment.R
import com.assignment.databinding.MainFragmentBinding
import com.assignment.ui.base.BaseFragment

class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>(R.layout.main_fragment) {

    override fun provideViewModelClass() = MainViewModel::class.java

    override val bindingVariable: Int = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchNews()

        viewModel.clickEvent.observe(this, {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(it)
            navController.navigate(action)
        })
    }
}