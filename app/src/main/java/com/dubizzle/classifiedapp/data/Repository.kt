package com.dubizzle.classifiedapp.data

import com.dubizzle.classifiedapp.data.remote.RemoteDataSource
import com.dubizzle.classifiedapp.model.BaseApiResponse
import com.dubizzle.classifiedapp.model.ClassifiedApiResponse
import com.dubizzle.classifiedapp.utils.NetworkApiResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getClassifieds(): Flow<NetworkApiResult<ClassifiedApiResponse>> {
        return flow {
            emit( safeApiCall { remoteDataSource.getClassifieds() })
        }.flowOn(Dispatchers.IO)
    }

}