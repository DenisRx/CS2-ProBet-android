package com.denisrx.cs2probet.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.denisrx.cs2probet.CS2ProBetApplication
import com.denisrx.cs2probet.ui.homeScreen.HomeViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(cs2ProBetApplication().container.teamRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [CS2ProBetApplication].
 */
fun CreationExtras.cs2ProBetApplication(): CS2ProBetApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CS2ProBetApplication)
