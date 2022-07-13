package com.soumik.newsapp.core.persistance

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.soumik.newsapp.BuildConfig
import com.soumik.newsapp.NewsApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
created by Soumik on 5/7/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_preferences")

object PrefManager {
    // getting the application context
    private val context = NewsApp.mContext

    fun getIntPref(key: String): Flow<Int> = context.dataStore.data
        .catch {
            Timber.e(this.toString(), "Exception in getIntPref(): $this")
            emit(emptyPreferences())
        }.map {
            it[intPreferencesKey(key)] ?: 0
        }

    suspend fun saveIntPref(key: String,value: Int) {
        Timber.d("saveIntPref(): ")
        context.dataStore.edit { prefs ->
            prefs[intPreferencesKey(key)] = value
        }
    }

    fun getLongPref(key: String) : Flow<Long> = context.dataStore.data
        .catch {
            Timber.e(this.toString(), "Exception in getLongPref(): $this")
            emit(emptyPreferences())
        }.map {
            it[longPreferencesKey(key)] ?: 0
        }

    suspend fun saveLongPref(key: String,value: Long) {
        Timber.d("saveLongPref(): ")
        context.dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    fun getBoolPref(key: String) : Flow<Boolean> = context.dataStore.data
        .catch {
            Timber.e(this.toString(), "Exception in getBoolPref(): $this")
            emit(emptyPreferences())
        }.map {
            it[booleanPreferencesKey(key)] ?: false
        }

    suspend fun saveBoolPref(key: String,value: Boolean) {
        Timber.d("saveBoolPref(): ")
        context.dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    fun getStringPref(key: String) : Flow<String> = context.dataStore.data
        .catch {
            Timber.e(this.toString(), "Exception in getStringPref(): $this")
            emit(emptyPreferences())
        }.map {
            it[stringPreferencesKey(key)] ?: ""
        }

    suspend fun saveStringPref(key: String,value: String) {
        Timber.d("saveStringPref(): ")
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }
}



