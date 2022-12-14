package com.viveksahani.datastore

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserDetailsModel(private val context: Context) {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("pref")

    companion object {
        private const val DATASTORE_PATH = "datastore/"
        val USERNAME = stringPreferencesKey("USER_NAME")
        val AGE = intPreferencesKey("AGE")
    }


    suspend fun storeUser(name: String, age: Int) {
        context.dataStore.edit {
            it[USERNAME] = name
            it[AGE] = age
        }
    }


    fun getUserName() = context.dataStore.data.map {
        it[USERNAME] ?: ""
    }

    fun getUserAge() = context.dataStore.data.map {
        it[AGE] ?: -1
    }

    suspend fun clear(){
        context.dataStore.edit {
            it.clear()
        }
    }



}
