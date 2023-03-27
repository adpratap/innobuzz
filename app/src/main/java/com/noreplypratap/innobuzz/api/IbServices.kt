package com.noreplypratap.innobuzz.api

import com.noreplypratap.innobuzz.model.UsersPostsModel
import retrofit2.Response
import retrofit2.http.GET

interface IbServices {
    @GET("/posts")
    suspend fun getDataFromAPI(): Response<ArrayList<UsersPostsModel>>

}

