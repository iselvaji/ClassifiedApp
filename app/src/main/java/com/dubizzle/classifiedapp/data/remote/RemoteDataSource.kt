package com.dubizzle.classifiedapp.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val classifiedService: ClassifiedService) {
    suspend fun getClassifieds() = classifiedService.getDetails()
}