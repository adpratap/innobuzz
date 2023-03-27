package com.noreplypratap.innobuzz.repository

import com.noreplypratap.innobuzz.api.IbServices
import com.noreplypratap.innobuzz.db.UsersPostDao
import com.noreplypratap.innobuzz.model.UsersPostsModel
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val ibServices: IbServices , private val usersPostDao: UsersPostDao) {
    suspend fun getUserPosts() = ibServices.getDataFromAPI()
    suspend fun saveToDB(data : List<UsersPostsModel>) = usersPostDao.saveIbUsersPostsToDatabase(data)
    fun getDataFromDB() = usersPostDao.getIbUsersPostsFromDatabase()

}
