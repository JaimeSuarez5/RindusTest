package es.jaimesuarez.rindustest.user.model

import android.os.Parcelable
import es.jaimesuarez.domain.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDisplay(
    val id: Int,
    val name: String,
    val company: String,
    val email: String,
    val website: String,
    val username: String
) : Parcelable {

    constructor(user: User) : this(
        id = user.id,
        name = user.name,
        company = user.company,
        email = user.email,
        website = user.website,
        username = user.username
    )
}
