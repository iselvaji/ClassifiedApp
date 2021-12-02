package com.dubizzle.classifiedapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubizzle.classifiedapp.data.Repository
import com.dubizzle.classifiedapp.model.ClassifiedApiResponse
import com.dubizzle.classifiedapp.utils.NetworkApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassifiedViewModel @Inject constructor (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val response: MutableLiveData<NetworkApiResult<ClassifiedApiResponse>> = MutableLiveData()

    fun fetchClassifiedResponse() = viewModelScope.launch {
        repository.getClassifieds().collect { values ->
             response.value = values
        }
    }
}