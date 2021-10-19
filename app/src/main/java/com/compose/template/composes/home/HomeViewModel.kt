package com.compose.template.composes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.template.MainRepository
import com.compose.template.models.Repository
import com.compose.template.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _trendingRepo = MutableStateFlow<Resource<List<Repository>>>(Resource.Loading())

    val trendingRepoState: StateFlow<Resource<List<Repository>>>
        get() = _trendingRepo

    init {
        refreshData()
    }

    fun refreshData() {
        if (_trendingRepo.value is Resource.Loading)
            return
        _trendingRepo.value = Resource.Loading()
        viewModelScope.launch {
            _trendingRepo.value = mainRepository.fetchRepositories()
        }
    }

}