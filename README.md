# ARAW
The Android Reddit API Wrapper

[![](https://jitpack.io/v/KirkBushman/ARAW.svg)](https://jitpack.io/#KirkBushman/ARAW)


### Info

ARAW is a reddit API Wrapper that target the android platform specifically.
It is built with Kotlin, Retrofit, Moshi, OkHttp.

ARAW it's hosted on Jitpack


### Documentation 

You can find the Docs at: [https://kirkbushman.github.io/ARAW/](https://kirkbushman.github.io/ARAW/)


### How to install.

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.KirkBushman:ARAW:Tag'
}
```

### Obtain a userless client

```kotlin
// step 1 - create the helper
val helper = AuthUserlessHelper(
    context = this,
    clientId = **CLIENT ID STRING**,
    deviceId = **DEVICE ID**, // set as null to use the default android UUID
    scopes = creds.scopes.toTypedArray(), // array of scopes strings
    logging = true
)

// use shouldLogin() to check whether authentication already happened
if (!helper.shouldLogin()) {
    // use saved one
} else {
    // you must authenticate
}

// step 2 - obtain a client 
val client = helper.getRedditClient()
```

### Obtain an installed app client

```kotlin
// step 1 - crete the help
val helper = AuthAppHelper(
    context = this,
    clientId = **CLIENT ID STRING**,
    redirectUrl = **REDIRECT URL**,
    scopes = creds.scopes.toTypedArray(), // array of scopes strings
    logging = true
)

// use shouldLogin() to check whether authentication already happened
if (!helper.shouldLogin()) {
    // use saved one
} else {
    // you must authenticate
}

// step 2 - obtain a client 
val client = helper.getRedditClient()
```

### Use it to browse Reddit

```kotlin
// get the authed account (not possible in the userless auth)
val me = client.accoutsClient.me()
println(me.toString())

// get the saved posts 
val fetcher = client.accountsClient.saved()
val saved = fetcher.fetchNext() // get the first page
val saved2 = fetcher.fetchNext() // get the second page

println(saved.toString())
println(saved2.toString())

// get the submissions from a subreddit
val fetcher = client.contributionsClient.submissions("pics")
val submissions = fetcher.fetchNext()

println(submissions.toString())

// get inbox messages
val fetcher = client?.messagesClient?.inbox()
val messages = fetcher.fetchNext()

println(messages.toString())
```

### License
This project is licensed under the MIT License
