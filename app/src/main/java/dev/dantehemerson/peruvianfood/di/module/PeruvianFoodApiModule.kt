
package dev.dantehemerson.peruvianfood.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.dantehemerson.peruvianfood.data.remote.api.PeruvianFoodService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class PeruvianFoodApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): PeruvianFoodService = Retrofit.Builder()
        .baseUrl(PeruvianFoodService.API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(PeruvianFoodService::class.java)
}
