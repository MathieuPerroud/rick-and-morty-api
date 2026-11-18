package org.mathieu.cleanrmapi.di

import android.app.Application
import androidx.room.Room
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterDao
import org.mathieu.cleanrmapi.data.local.CharacterDatabase
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.createHttpClient
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository

//https://rickandmortyapi.com/documentation/#rest
private const val RMAPI_URL = "https://rickandmortyapi.com/api/"

/**
 * Builds the Room database of the local character store.
 *
 * Database name and destructive fallback carried over verbatim from this repository's
 * proven Room wiring; the destructive fallback mirrors the former Realm configuration's
 * `deleteRealmIfMigrationNeeded()` posture.
 */
private fun provideDataBase(application: Application): CharacterDatabase =
    Room.databaseBuilder(
        application,
        CharacterDatabase::class.java,
        "rick_and_morty_database"
    ).
    fallbackToDestructiveMigration().build()

val dataModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RMAPI_URL
        )
    }

    single { provideDataBase(androidApplication()) }

    single { get<CharacterDatabase>().characterDao() }

    single { CharacterApi(get()) }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }

}