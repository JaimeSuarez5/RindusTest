package es.jaimesuarez.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val website: String,
    val company: String,
    val addressLocation: AddressLocation
)

data class AddressLocation(
    val lat: String,
    val lng: String
)

