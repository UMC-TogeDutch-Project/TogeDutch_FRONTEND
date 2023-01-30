package umc.mobile.project.signup

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


data class SignUpResponse





 (
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: SignUpResult?
)

data class SignUpResult(
    @SerializedName("userIdx")
    var userIdx: Int,

    @SerializedName("keywordIdx")
    var keywordIdx: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("role")
    var role: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("created_at")
    var created_at: Timestamp,

    @SerializedName("updated_at")
    var updated_at: Timestamp,

    @SerializedName("latitude")
    var latitude: Double,

    @SerializedName("longitude")
    var longitude: Double,

    @SerializedName("jwt")
    var jwt: String

)
