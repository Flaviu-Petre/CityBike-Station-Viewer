package com.example.citybikeviewer.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "favorites")
data class FavoriteNetwork(
    @PrimaryKey val id: String,
    val name: String,
    val city: String,
    val country: String
)

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(network: FavoriteNetwork)

    @Delete
    suspend fun delete(network: FavoriteNetwork)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteNetwork>>

//    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
//    fun isFavorite(id: String): Flow<Boolean>
}

@Database(entities = [FavoriteNetwork::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}