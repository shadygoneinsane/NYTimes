package com.assignment.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.assignment.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<D : ViewDataBinding, V : BaseViewModel>(@LayoutRes val layoutRes: Int) :
    DaggerFragment() {
    abstract fun provideViewModelClass(): Class<out V>
    abstract val bindingVariable: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var dataBinding: D

    protected lateinit var viewModel: V
    private var progressAlertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(provideViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    protected fun setToolbarTitle(title : String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.setVariable(bindingVariable, viewModel)
        dataBinding.executePendingBindings()
        initViewSubscriptions()
    }

    private fun initViewSubscriptions() {
        (viewModel as BaseViewModel).errorEvent.observe(
            viewLifecycleOwner, { showErrorDialog(it.errorMessage) }
        )

        (viewModel as? BaseViewModel)?.loadingEvent?.observe(
            viewLifecycleOwner, { loadingInProgress: Boolean -> observeLoading(loadingInProgress) }
        )
    }

    /**
     * Method to observer loading
     */
    private fun observeLoading(loadingInProgress: Boolean) {
        hideLoading()
        if (loadingInProgress) showLoading()
    }

    /**
     * Method to show loading
     */
    protected fun showLoading() {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_progress, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        progressAlertDialog = dialogBuilder.create()
        progressAlertDialog?.show()
        progressAlertDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    /**
     * Method to hide loading
     */
    protected fun hideLoading() {
        progressAlertDialog?.dismiss()
    }

    /**
     * Method to show service related error messages
     */
    protected fun showErrorDialog(message: String?) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(requireActivity().getString(R.string.title_error))
            .setMessage(message)
            .setPositiveButton(requireActivity().getString(R.string.action_ok), null)
            .create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}