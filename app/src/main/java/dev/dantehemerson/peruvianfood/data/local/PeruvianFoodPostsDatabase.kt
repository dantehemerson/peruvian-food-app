
package dev.dantehemerson.peruvianfood.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.dantehemerson.peruvianfood.data.local.dao.PostsDao
import dev.dantehemerson.peruvianfood.model.Post

/**
 * Abstract PeruvianFood database.
 * It provides DAO [PostsDao] by using method [getPostsDao].
 */
@Database(
    entities = [Post::class],
    version = DatabaseMigrations.DB_VERSION
)
abstract class PeruvianFoodPostsDatabase : RoomDatabase() {

    /**
     * @return [PostsDao] PeruvianFood Posts Data Access Object.
     */
    abstract fun getPostsDao(): PostsDao

    companion object {
        const val DB_NAME = "peruvian_food_database"

        @Volatile
        private var INSTANCE: PeruvianFoodPostsDatabase? = null

        fun getInstance(context: Context): PeruvianFoodPostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeruvianFoodPostsDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
