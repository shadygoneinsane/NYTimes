package com.assignment.ui.main

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.BR
import com.assignment.BuildConfig
import com.assignment.R
import com.assignment.arc.SingleLiveEvent
import com.assignment.domain.common.ResultState
import com.assignment.domain.entity.response.news.NYEntity
import com.assignment.domain.repository.NewsRepository
import com.assignment.ui.base.BaseViewModel
import com.assignment.ui.interfaces.OnOptionClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataRepository: NewsRepository) :
    BaseViewModel() {
    private val clickEvent = SingleLiveEvent<NewsItemsExtended>()
    private val showErrorView = MutableLiveData<Boolean>()
    private var shouldHideRefresh = SingleLiveEvent<Boolean>()
    private var newsList = ObservableArrayList<NewsItemsExtended>()
    private var isSwipeToRefresh: Boolean = false

    fun getShowErrorView(): MutableLiveData<Boolean> = showErrorView

    fun getList(): ObservableArrayList<NewsItemsExtended> = newsList

    fun getItemClick() = clickEvent

    fun getShouldHideRefresh() = shouldHideRefresh

    /**
     * Method to be invoked on Swipe to refresh
     */
    fun swipeRefreshEvent() {
        isSwipeToRefresh = true
        fetchNews(false)
    }

    fun fetchNews(showLoading: Boolean) {
        showLoading(showLoading)
        viewModelScope.launch {
            dataRepository.fetchMostViewedNews(BuildConfig.APP_ID, 1)
                .collect { state ->
                    when (state) {
                        is ResultState.Success -> {
                            prepareNewsList(state.data.results)
                        }
                        is ResultState.Error -> {
                            setError(error = state.error)
                        }
                    }
                    showLoading(false)
                    shouldHideRefresh.value = true
                }
        }
    }

    /**
     * The binding item for recyclerview.
     */
    val accessoryBinding: OnItemBindClass<NewsItemsExtended> by lazy {
        OnItemBindClass<NewsItemsExtended>()
            .map(
                NewsItemsExtended::class.java
            ) { itemBinding: ItemBinding<*>, position: Int, item: NewsItemsExtended ->
                itemBinding.clearExtras().set(BR.item, R.layout.item_article_content)
                    .bindExtra(
                        BR.listener,
                        object : OnOptionClickListener<NewsItemsExtended> {
                            override fun onOptionClick(option: NewsItemsExtended) {
                                Timber.d("Title clicked : ${option.title}")
                                clickEvent.value = option
                            }
                        } as OnOptionClickListener<NewsItemsExtended>)
            }
    }

    /**
     * Prepares the newslist observable for rendering
     * @param newsListResponse: List<NYEntity.NewsResult>
     */
    fun prepareNewsList(newsListResponse: List<NYEntity.NewsResult>) {
        for (newsItem in newsListResponse) {
            newsList.add(NewsItemsExtended(newsItem))
        }
    }

    inner class NewsItemsExtended(newsEntity: NYEntity.NewsResult) : NYEntity.NewsResult(
        newsEntity.uri, newsEntity.url, newsEntity.publishedDate,
        newsEntity.section, newsEntity.byline, newsEntity.title, newsEntity.media
    ) {
        val mediaUrl: ObservableField<String?> =
            ObservableField(newsEntity.media.firstOrNull()?.mediaMetadata?.firstOrNull()?.url)
    }
}