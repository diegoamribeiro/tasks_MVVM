package com.diegoribeiro.taskwars.model

import com.google.gson.annotations.SerializedName

data class Task(

    @SerializedName("id")
    var id: Long,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
)
