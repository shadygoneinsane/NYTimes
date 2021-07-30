package com.assignment.ui.base

import androidx.lifecycle.ViewModel
import com.assignment.arc.SingleLiveEvent
import com.assignment.domain.entity.response.common.ErrorEntity

abstract class BaseViewModel : ViewModel() {
    /**
     * Live data to handle error
     */
    val errorEvent = SingleLiveEvent<ErrorEntity.Error>()

    /**
     * Live data to handle loading
     */
    val loadingEvent = SingleLiveEvent<Boolean>()

    /**
     * Method call to handle error
     */
    fun setError(error: ErrorEntity.Error?) {
        errorEvent.value = error
    }

    fun showLoading(show: Boolean) {
        loadingEvent.value = show
    }

    fun postLoading(show: Boolean) {
        loadingEvent.postValue(show)
    }

    fun getLoading(): SingleLiveEvent<Boolean> {
        return loadingEvent
    }
}