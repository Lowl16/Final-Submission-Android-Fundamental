package com.dicoding.githubuser.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.githubuser.preferences.SettingPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SettingViewModelTest {

    private lateinit var viewModel: SettingViewModel

    @Mock
    private lateinit var pref: SettingPreferences

    @Mock
    private lateinit var observer: Observer<Boolean>

    @get:Rule
    var instantTaskExcecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SettingViewModel(pref)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun testGetThemeSettings() {
        val isDarkModeActive = true
        val flow = flow {
            emit(isDarkModeActive)
        }
        `when`(pref.getThemeSetting()).thenReturn(flow)

        val liveData = viewModel.getThemeSettings()
        liveData.observeForever(observer)

        verify(observer).onChanged(isDarkModeActive)
    }

    @Test
    fun testSaveThemeSettings() = runBlocking {
        val isDarkModeActive = true
        viewModel.saveThemeSetting(isDarkModeActive)

        verify(pref).saveThemeSetting(isDarkModeActive)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }
}