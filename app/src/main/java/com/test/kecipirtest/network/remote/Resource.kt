package com.test.kecipirtest.network.remote

sealed class Resource<T>{
    class Loading<T> : Resource<T>()
    class Empty<T> : Resource<T>()
    class Success<T>(val data:T) : Resource<T>()
    class Error<T>(val message:String?): Resource<T>()

    companion object{
        fun <T> loading(): Resource<T> = Loading()
        fun <T> empty(): Resource<T> = Empty()
        fun <T> success(data:T): Resource<T> = Success(data)
        fun <T> error(message: String?): Resource<T> = Error(message)
    }
}