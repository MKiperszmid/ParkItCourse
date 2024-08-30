package com.mkiperszmid.parkitcourse.home.di

import android.content.Context
import androidx.room.Room
import com.mkiperszmid.parkitcourse.home.data.HomeRepositoryImpl
import com.mkiperszmid.parkitcourse.home.data.LocationServiceImpl
import com.mkiperszmid.parkitcourse.home.data.local.HomeDao
import com.mkiperszmid.parkitcourse.home.data.local.HomeDatabase
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideHomeRepository(dao: HomeDao): HomeRepository {
        return HomeRepositoryImpl(dao)
    }
}