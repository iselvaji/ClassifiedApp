package com.dubizzle.classifiedapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dubizzle.classifiedapp.data.Repository
import com.dubizzle.classifiedapp.utils.NetworkApiResult
import com.dubizzle.classifiedapp.viewmodel.ClassifiedViewModel
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ClassfiedViewmodelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: ClassifiedViewModel

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<in Any>

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        var app = mock(ClassifiedApplication::class.java)

        viewModel = ClassifiedViewModel(repository, app).apply {
            response.observeForever(observer)
        }
        //viewModel = spy(ClassifiedViewModel(repository, app))
    }

    @Test
    fun verifyLivedataChanges() = coroutineTestRule.runBlockingTest {

        val data = Any()
        assertNotNull(viewModel.fetchClassifiedResponse())
       // `when`(viewModel.fetchClassifiedResponse()).thenReturn(data)
        viewModel.fetchClassifiedResponse()
        delay(50)
        verify(observer).onChanged(isA(NetworkApiResult.Loading::class.java))
        verify(observer).onChanged(isA(NetworkApiResult.Success::class.java))
    }

    @Test
    fun verifyLivedataError() = coroutineTestRule.runBlockingTest {

        val error = Error()
        assertNotNull(viewModel.fetchClassifiedResponse())
        // viewModel.response.getOrAwaitValue()
        `when`(viewModel.fetchClassifiedResponse()).thenThrow(error)
        viewModel.fetchClassifiedResponse()
        delay(50)
        verify(observer).onChanged(isA(NetworkApiResult.Loading::class.java))
        verify(observer).onChanged(isA(NetworkApiResult.Error::class.java))
    }
}