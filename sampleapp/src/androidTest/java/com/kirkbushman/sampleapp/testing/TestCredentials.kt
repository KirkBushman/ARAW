package com.kirkbushman.sampleapp.testing

data class TestCredentials(

    val clientId: String,
    val clientSecret: String,

    val scopes: List<String>,

    val username: String,
    val password: String
)
