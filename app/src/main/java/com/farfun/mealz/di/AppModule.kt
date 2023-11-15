package com.farfun.mealz.di

import android.app.Application
import androidx.room.Room
import com.farfun.mealz.data.dao.MealsDbDao
import com.farfun.mealz.data.db.MealsDb
import com.farfun.mealz.remote.MealsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMealsApi(): MealsApi {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMealsDb(
        app: Application
    ): MealsDb {
        return Room.databaseBuilder(
            app,
            MealsDb::class.java,
            "meals_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMealsDao(
        mealsDb: MealsDb
    ): MealsDbDao {
        return mealsDb.mealsDbDao()
    }
}