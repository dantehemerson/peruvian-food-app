
package dev.dantehemerson.peruvianfood.data.repository

import androidx.annotation.MainThread
import dev.dantehemerson.peruvianfood.data.local.dao.PostsDao
import dev.dantehemerson.peruvianfood.data.remote.api.PeruvianFoodService
import dev.dantehemerson.peruvianfood.model.Post
import dev.dantehemerson.peruvianfood.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
@ExperimentalCoroutinesApi
@Singleton
class PostsRepository @Inject constructor(
    private val postsDao: PostsDao,
    private val peruvianFoodService: PeruvianFoodService
) {

    /**
     * Fetched the posts from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getAllPosts(): Flow<State<List<Post>>> {
        return object : NetworkBoundRepository<List<Post>, List<Post>>() {

            override suspend fun saveRemoteData(response: List<Post>) =
                postsDao.insertPosts(response)

            override fun fetchFromLocal(): Flow<List<Post>> = postsDao.getAllPosts()

            override suspend fun fetchFromRemote(): Response<List<Post>> = peruvianFoodService.getPosts()
        }.asFlow()
    }

    /**
     * Retrieves a post with specified [postId].
     * @param postId Unique id of a [Post].
     * @return [Post] data fetched from the database.
     */
    @MainThread
    fun getPostById(postId: Int): Flow<Post> = postsDao.getPostById(postId)
}
