package umc.mobile.project.signup

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Request(

    @SerializedName("KeywordIdx")
    val keywordIdx: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,





    )
