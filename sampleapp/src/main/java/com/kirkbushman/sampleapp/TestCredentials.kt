package com.kirkbushman.sampleapp

data class TestCredentials(

    val clientId: String,
    val redirectUrl: String,

    val scriptClientId: String,
    val scriptClientSecret: String,
    val username: String,
    val password: String,

    val scopes: List<String>
)
