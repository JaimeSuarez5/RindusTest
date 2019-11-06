package es.jaimesuarez.data.model

import com.google.gson.annotations.SerializedName
import es.jaimesuarez.domain.model.AddressLocation
import es.jaimesuarez.domain.model.User

data class UserRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("website") val website: String,
    @SerializedName("company") val company: CompanyRemote,
    @SerializedName("address") val addressRemote: AddressRemote
) {

    fun toDomain() = User(
        id = id,
        name = name,
        username = username,
        email = email,
        website = website,
        company = company.name,
        addressLocation = addressRemote.toDomain()
    )
}

data class CompanyRemote(
    @SerializedName("name") val name: String
)

data class AddressRemote(
    @SerializedName("geo") val geo: GeolocationRemote
) {

    fun toDomain() = AddressLocation(
        lat = geo.lat,
        lng = geo.lng
    )
}

data class GeolocationRemote(
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String
)
