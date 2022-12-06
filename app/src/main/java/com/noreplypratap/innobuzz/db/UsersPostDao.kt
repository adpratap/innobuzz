package com.noreplypratap.innobuzz.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noreplypratap.innobuzz.model.UsersPostsModel

@Dao
interface UsersPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveIbUsersPostsToDatabase(article: List<UsersPostsModel>)

    @Query("SELECT * From UsersPostsTable")
    fun getIbUsersPostsFromDatabase() : LiveData<List<UsersPostsModel>>

    @Query("DELETE FROM UsersPostsTable")
    suspend fun deletePostsFromDB()
}