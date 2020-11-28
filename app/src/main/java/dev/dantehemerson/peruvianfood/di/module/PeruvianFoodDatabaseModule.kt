
package dev.dantehemerson.peruvianfood.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.dantehemerson.peruvianfood.data.local.PeruvianFoodPostsDatabase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class PeruvianFoodDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = PeruvianFoodPostsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: PeruvianFoodPostsDatabase) = database.getPostsDao()
}
