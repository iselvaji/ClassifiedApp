package com.dubizzle.classifiedapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dubizzle.classifiedapp.data.remote.ClassifiedService
import com.dubizzle.classifiedapp.data.remote.RemoteDataSource
import com.dubizzle.classifiedapp.data.Repository
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.MockResponseFileReader

@RunWith(JUnit4::class)
class ClassifiedApiTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var repository: Repository
    private lateinit var mockedResponse: String

    private val gson = GsonBuilder().setLenient().create()

    @Before
    fun init() {

        server.start(8000)
        val baseUrl = server.url("/").toString()

        val okHttpClient = OkHttpClient.Builder().build()

        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build().create(ClassifiedService::class.java)

        repository = Repository(RemoteDataSource(service))
    }

    @Test
    fun testApiSuccess() = runBlocking {
        mockedResponse = MockResponseFileReader("classified/success.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        //val response = runBlocking { repository.getClassifieds() }
        val response = repository.getClassifieds()
        response.collect { res ->
            val json = gson.toJson(res)

            val resultResponse = JsonParser().parse(json).asJsonObject
            val expectedResponse = JsonParser().parse(mockedResponse).asJsonObject

            Assert.assertNotNull(response)
            Assert.assertTrue(resultResponse.equals(expectedResponse))
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}