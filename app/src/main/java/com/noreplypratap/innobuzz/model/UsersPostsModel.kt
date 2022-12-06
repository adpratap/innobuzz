package com.noreplypratap.innobuzz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UsersPostsTable")
data class UsersPostsModel(
    var body: String?,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String?,
    var userId: Int?
)