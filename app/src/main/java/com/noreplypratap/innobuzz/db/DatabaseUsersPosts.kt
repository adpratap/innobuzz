package com.noreplypratap.innobuzz.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noreplypratap.innobuzz.model.UsersPostsModel

@Database(
    entities = [UsersPostsModel::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseUsersPosts : RoomDatabase() {
    abstract fun getPostDao() : UsersPostDao

    companion object{
        @Volatile
        private var instance : DatabaseUsersPosts? = null

        fun createDatabase(context: Context) : DatabaseUsersPosts {

            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseUsersPosts::class.java,
                        "IbUsersPostsData"
                    ).build()
                }
            }
            return instance!!
        }
    }

}