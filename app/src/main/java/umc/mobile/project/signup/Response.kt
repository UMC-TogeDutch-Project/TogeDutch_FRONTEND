package umc.mobile.project.signup

import java.sql.Timestamp


data class Response(
    var userIdx: Int,
    var keywordIdx: Int,
    var name: String,
    var role: String,
    var email: String,
    var password: String,
    var phone: String,
    var image: String,
    var status: String,
    var created_at: Timestamp,
    var updated_at: Timestamp,
    var latitude: Int,
    var longitude: Int,
    var jwt: String
)
