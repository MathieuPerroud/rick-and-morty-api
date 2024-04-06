package org.mathieu.cleanrmapi.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.HttpClient
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://rickandmortyapi.com/documentation/#rest
private const val RMAPI_URL = "https://rickandmortyapi.com/api/"

private fun provideHttpClient(): OkHttpClient =
    HttpClient().client

private val gson = GsonBuilder()
    .serializeNulls() // Configure Gson to include null values
    .create()

private fun buildRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .baseUrl(RMAPI_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

private fun provideDataBase(application: Application): RMDatabase =
    Room.databaseBuilder(
        application,
        RMDatabase::class.java,
        "rick_and_morty_database"
    ).
    fallbackToDestructiveMigration().build()

private inline fun <reified T> provideApi(httpClient: OkHttpClient): T = buildRetrofit(
    okHttpClient = httpClient
).create(T::class.java)

val dataModule = module {

    single { provideHttpClient() }

    single { provideApi<CharacterApi>(get()) }

    single { provideDataBase(get()) }

    single<CharacterRepository> {
        val db: RMDatabase = get()

        CharacterRepositoryImpl(get(), get(), get<RMDatabase>().characterDAO())
    }

}