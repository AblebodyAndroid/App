package com.smilehunter.ablebody.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.PersonHeightFilterType
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.data.result.Result
import com.smilehunter.ablebody.data.result.asResult
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.model.SearchHistoryQuery
import com.smilehunter.ablebody.domain.usecase.DeleteSearchHistoryUseCase
import com.smilehunter.ablebody.domain.usecase.GetRecommendKeywordUseCase
import com.smilehunter.ablebody.domain.usecase.GetSearchCodyItemPagerUseCase
import com.smilehunter.ablebody.domain.usecase.GetSearchHistoryUseCase
import com.smilehunter.ablebody.domain.usecase.GetSearchProductItemPagerUseCase
import com.smilehunter.ablebody.presentation.search.data.KeywordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getSearchProductItemPagerUseCase: GetSearchProductItemPagerUseCase,
    getSearchCodyItemPagerUseCase: GetSearchCodyItemPagerUseCase,
    getRecommendKeywordUseCase: GetRecommendKeywordUseCase,
    getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase
) : ViewModel() {

    private val _networkRefreshFlow = MutableSharedFlow<Unit>()
    private val networkRefreshFlow = _networkRefreshFlow.asSharedFlow()

    fun refreshNetwork() {
        viewModelScope.launch { _networkRefreshFlow.emit(Unit) }
    }

    private val _keyword = MutableStateFlow("")
    val keyword = _keyword.asStateFlow()

    fun updateKeyword(keyword: String) {
        viewModelScope.launch {
            _keyword.emit(keyword)
        }
    }

    val recommendedKeywords: StateFlow<KeywordUiState> =
        flow { emit(getRecommendKeywordUseCase()) }
            .asResult()
            .map {
                when (it) {
                    is Result.Error -> KeywordUiState.LoadFail(it.exception)
                    is Result.Loading -> KeywordUiState.Loading
                    is Result.Success -> KeywordUiState.RecommendKeyword(it.data)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = KeywordUiState.Loading
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchHistoryQueries: StateFlow<List<SearchHistoryQuery>> =
        getSearchHistoryUseCase()
            .flatMapLatest {
                flowOf(it.asReversed())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            deleteSearchHistoryUseCase()
        }
    }

    private val _productItemSortingMethod = MutableStateFlow(SortingMethod.POPULAR)
    val productItemSortingMethod = _productItemSortingMethod.asStateFlow()

    fun updateProductItemSortingMethod(sortingMethod: SortingMethod) {
        viewModelScope.launch {
            _productItemSortingMethod.emit(sortingMethod)
        }
    }

    private val _productItemGender = MutableStateFlow(ItemGender.MALE)
    val productItemGender = _productItemGender.asStateFlow()

    fun updateProductItemGender(gender: ItemGender) {
        viewModelScope.launch {
            _productItemGender.emit(gender)
        }
    }

    private val _productItemParentCategory = MutableStateFlow(ItemParentCategory.ALL)
    val productItemParentCategory = _productItemParentCategory.asStateFlow()

    fun updateProductItemParentFilter(parentCategory: ItemParentCategory) {
        viewModelScope.launch {
            _productItemParentCategory.emit(parentCategory)
            _productItemChildCategory.emit(null)
        }
    }

    private val _productItemChildCategory = MutableStateFlow<ItemChildCategory?>(null)
    val productItemChildCategory = _productItemChildCategory.asStateFlow()

    fun updateBrandProductItemChildFilter(childCategory: ItemChildCategory?) {
        viewModelScope.launch {
            _productItemChildCategory.emit(childCategory)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val productPagingItemList: StateFlow<PagingData<ProductItemData.Item>> =
        networkRefreshFlow.onSubscription { emit(Unit) }
            .flatMapLatest {
                combine(
                    productItemSortingMethod,
                    keyword.debounce(1000L),
                    productItemGender,
                    productItemParentCategory,
                    productItemChildCategory
                ) { sort, keyword, gender, parent, child ->
                    if (keyword.isNotEmpty()) {
                        getSearchProductItemPagerUseCase(sort, keyword, gender, parent, child)
                    } else {
                        flowOf(PagingData.empty())
                    }
                }
                    .flatMapLatest { it }
                    .cachedIn(viewModelScope)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )

    fun resetCodyItemFilter() {
        viewModelScope.launch {
            _codyItemListGenderFilter.emit(emptyList())
            _codyItemListSportFilter.emit(emptyList())
            _codyItemListPersonHeightFilter.emit(PersonHeightFilterType.ALL)
        }
    }

    private val _codyItemListGenderFilter = MutableStateFlow<List<Gender>>(listOf())
    val codyItemListGenderFilter = _codyItemListGenderFilter.asStateFlow()

    fun updateCodyItemListGenders(genders: List<Gender>) {
        viewModelScope.launch { _codyItemListGenderFilter.emit(genders) }
    }

    private val _codyItemListSportFilter = MutableStateFlow<List<HomeCategory>>(listOf())
    val codyItemListSportFilter = _codyItemListSportFilter.asStateFlow()

    fun updateCodyItemListSportFilter(sports: List<HomeCategory>) {
        viewModelScope.launch { _codyItemListSportFilter.emit(sports) }
    }

    private val _codyItemListPersonHeightFilter = MutableStateFlow(PersonHeightFilterType.ALL)
    val codyItemListPersonHeightFilter = _codyItemListPersonHeightFilter.asStateFlow()

    fun updateCodyItemListPersonHeightFilter(sports: PersonHeightFilterType) {
        viewModelScope.launch { _codyItemListPersonHeightFilter.emit(sports) }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val codyPagingItemList: StateFlow<PagingData<CodyItemData.Item>> =
        networkRefreshFlow.onSubscription { emit(Unit) }
            .flatMapLatest {
                combine(
                    keyword.debounce(1000L),
                    codyItemListGenderFilter,
                    codyItemListSportFilter,
                    codyItemListPersonHeightFilter
                ) { keyword, gender, sport, height ->
                    if (keyword.isNotEmpty()) {
                        getSearchCodyItemPagerUseCase(
                            keyword = keyword,
                            genders = gender,
                            category = sport,
                            personHeightRangeStart = height.rangeStart,
                            personHeightRangeEnd = height.rangeEnd
                        )
                    } else {
                        flowOf(PagingData.empty())
                    }
                }
                    .flatMapLatest { it }
                    .cachedIn(viewModelScope)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )
}