package com.example.openweather.core

data class Outcome<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Outcome<T> {
            return Outcome(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Outcome<T> {
            return Outcome(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Outcome<T> {
            return Outcome(Status.LOADING, data, null)
        }
    }
}

enum class Status{
    SUCCESS,
    LOADING,
    ERROR,
}