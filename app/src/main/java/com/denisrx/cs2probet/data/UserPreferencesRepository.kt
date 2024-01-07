package com.denisrx.cs2probet.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun getScore(): Int = dataStore.data.first().let { it[SCORE] ?: 0 }

    suspend fun getScoreEvolution(): Int = dataStore.data.first().let { it[SCORE_EVOLUTION] ?: 0 }

    suspend fun saveScore(score: Int) {
        dataStore.edit { preferences ->
            preferences[SCORE] = score
        }
    }

    suspend fun saveScoreEvolution(scoreEvolution: Int) {
        dataStore.edit { preferences ->
            preferences[SCORE_EVOLUTION] = scoreEvolution
        }
    }

    private companion object {
        val SCORE = intPreferencesKey("score")
        val SCORE_EVOLUTION = intPreferencesKey("score_evolution")
    }
}
