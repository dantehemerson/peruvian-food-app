
package dev.dantehemerson.peruvianfood.data.remote.api

import dev.dantehemerson.peruvianfood.BuildConfig
import dev.dantehemerson.peruvianfood.data.remote.api.PeruvianFoodService.Companion.API_URL
import dev.dantehemerson.peruvianfood.model.Post
import retrofit2.Response
import retrofit2.http.GET

/**
 * Service to fetch PeruvianFood posts using dummy end point [API_URL].
 */
interface PeruvianFoodService {

    @GET("/dishes/")
    suspend fun getPosts(): Response<List<Post>>

    companion object {
        const val API_URL = BuildConfig.PERUVIAN_FOOD_API
    }
}
