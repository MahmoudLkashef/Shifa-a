package com.syncdev.domain.utils

/**
 * A sealed class representing the different states of a network request.
 *
 * This class provides a simplified and type-safe way to handle the different states of a network request.
 *
 * @param T The type of data returned by the network request.
 * @property data The data returned by the network request, or null if there was an error.
 * @property message The error message, or null if the request was successful.
 */
sealed class State<T>(
    val data: T? = null,
    val message: String? = null
){
    /**
     * Represents a successful network request.
     *
     * @param data The data returned by the network request.
     */
    class Success<T>(data:T):State<T>(data)

    /**
     * Represents an unsuccessful network request.
     *
     * @param message The error message returned by the network request.
     * @param data The data returned by the network request, if any.
     */
    class Error<T>(message: String, data: T?=null):State<T>(data, message)
}
