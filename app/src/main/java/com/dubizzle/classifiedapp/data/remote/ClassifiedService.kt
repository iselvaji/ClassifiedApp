package com.dubizzle.classifiedapp.data.remote

import com.dubizzle.classifiedapp.model.ClassifiedApiResponse
import com.dubizzle.classifiedapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ClassifiedService {
    @GET(Constants.CLASSIFIED_END_POINT)
    suspend fun getDetails(): Response<ClassifiedApiResponse>
}