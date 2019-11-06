# Rindus Test

## Overview

- Kotlin
- Coroutines
- MVVM with LiveData
- Jetpack Navigation Component
- Clean architecture. Three modules: app, domain and data
- Caching logic
- Some of the libraries used: Koin, Retrofit2, MockK, Glide...

<img src="https://github.com/JaimeSuarez5/RindusTest/blob/develop/fetch_user/images/rindus_test_video.gif" width="280" />

## Functionality
- Display users and its albums, todo's and posts
- Detail of nested resources of albums and posts
- Addition of new todo's
- Deletion of todo's on swipe

## API Usage

The following endpoints have been implemented:
- GET /users
- GET /users/{userId}/albums
- GET /users/{userId}/todos
- GET /users/{userId}/posts
- GET /albums/{albumId}/photos
- GET /posts/{postId}/comments
- POST /todos
- DELETE /todos/{todoId}

## Security
- Add login support in API side providing an standard protocol like OAuth2.
- HTTPS in the API communications.
- Avoid saving sensitive data in the device.
- Add code obfuscation to avoid possible reverse engineering

## Future improvements
- Better caching support. It would be good to persist the network data in a better way to reduce the network calls and improve the performance.
- Split the modules by feature, instead of by layers. It will improve the build performance and allow dynamic delivery.
- Add more tests to increment the test coverage.
- Add code obfuscation with ProGuard or R8.

