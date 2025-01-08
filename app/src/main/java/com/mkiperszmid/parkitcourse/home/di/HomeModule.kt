package com.mkiperszmid.parkitcourse.home.di

import android.content.Context
import androidx.room.Room
import com.mkiperszmid.parkitcourse.home.data.FirebaseFeatureFlag
import com.mkiperszmid.parkitcourse.home.data.HomeRepositoryImpl
import com.mkiperszmid.parkitcourse.home.data.LocationServiceImpl
import com.mkiperszmid.parkitcourse.home.data.distance.DistanceCalculatorImpl
import com.mkiperszmid.parkitcourse.home.data.local.HomeDao
import com.mkiperszmid.parkitcourse.home.data.local.HomeDatabase
import com.mkiperszmid.parkitcourse.home.data.remote.DirectionsApi
import com.mkiperszmid.parkitcourse.home.data.remote.interceptor.MapsApiRestrictionInterceptor
import com.mkiperszmid.parkitcourse.home.domain.FeatureFlag
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.LocationService
import com.mkiperszmid.parkitcourse.home.domain.distance.DistanceCalculator
import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.usecase.GetPathToCarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): LocationService {
        return LocationServiceImpl(context)
    }

    @Provides
    @Singleton
    fun provideHomeDao(@ApplicationContext context: Context): HomeDao {
        return Room.databaseBuilder(context, HomeDatabase::class.java, "parkit.db").build().dao
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).addInterceptor(MapsApiRestrictionInterceptor(context)).build()
    }

    @Provides
    @Singleton
    fun provideDirectionsApi(client: OkHttpClient): DirectionsApi {
        return Retrofit.Builder().baseUrl(DirectionsApi.BASE_URL).client(client)
            .addConverterFactory(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }.asConverterFactory("application/json".toMediaType())
            ).build().create()
    }


    @Provides
    @Singleton
    fun provideHomeRepository(dao: HomeDao, api: DirectionsApi): HomeRepository {
        return HomeRepositoryImpl(dao, api)
    }

    @Provides
    @Singleton
    fun provideGetPathToCarUseCase(
        repository: HomeRepository,
        distanceCalculator: DistanceCalculator
    ): GetPathToCarUseCase {
        return GetPathToCarUseCase(repository, distanceCalculator)
    }

    @Provides
    @Singleton
    fun provideDistanceCalculator(): DistanceCalculator {
        return DistanceCalculatorImpl()
    }

    @Provides
    @Singleton
    fun provideFeatureFlag(): FeatureFlag {
        return FirebaseFeatureFlag()
    }


}