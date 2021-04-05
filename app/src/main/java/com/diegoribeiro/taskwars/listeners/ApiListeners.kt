package com.diegoribeiro.taskwars.listeners

interface ApiListener<T> {

    fun onSuccess(model: T)
    fun onFailure(str: String)
}