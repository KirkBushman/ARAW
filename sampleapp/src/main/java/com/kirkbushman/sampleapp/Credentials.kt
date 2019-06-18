package com.kirkbushman.sampleapp

data class Credentials(
    val clientId: String,
    val redirectUrl: String,
    val scopes: List<String>
) {

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is Credentials) {
            return false
        }

        return clientId == other.clientId &&
                redirectUrl == other.redirectUrl &&
                scopes.size == other.scopes.size
    }

    override fun hashCode(): Int {
        return (clientId + redirectUrl + scopes.size).hashCode()
    }

    override fun toString(): String {
        return "Credentials { clientId: $clientId, redirectUrl: $redirectUrl, scopes: $scopes }"
    }
}