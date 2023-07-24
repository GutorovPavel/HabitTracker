package com.example.habittracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.habittracker.data.DataStoreRepository
import com.example.habittracker.data.HabitsRepositoryImpl
import com.example.habittracker.data.local.db.HabitsDatabase
import com.example.habittracker.domain.repository.HabitsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context)

    @Provides
    @Singleton
    fun provideHabitsDatabase(app: Application): HabitsDatabase {
        return Room.databaseBuilder(
            app,
            HabitsDatabase::class.java,
            "habitsdb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitsRepository(db: HabitsDatabase): HabitsRepository {
        return HabitsRepositoryImpl(db.dao)
    }
}