package com.kirkbushman.sampleapp.testing

data class Credentials(

    val clientId: String,
    val redirectUrl: String,
    val scopes: List<String>,

    val username: String,
    val password: String
)
