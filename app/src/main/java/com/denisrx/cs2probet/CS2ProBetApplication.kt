package com.denisrx.cs2probet

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.denisrx.cs2probet.data.AppContainer
import com.denisrx.cs2probet.data.DefaultAppContainer
import com.denisrx.cs2probet.data.UserPreferencesRepository

private const val SCORE_PREFERENCE_NAME = "score_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SCORE_PREFERENCE_NAME,
)

/*
 * Custom app entry point for manual dependency injection
 */
class CS2ProBetApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
