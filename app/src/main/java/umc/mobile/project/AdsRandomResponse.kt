package umc.mobile.project

import com.google.gson.annotations.SerializedName
import umc.mobile.project.signup.SignUpResult
import java.sql.Timestamp

data class AdsRandomResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: AdsRandomResult?
    )

data class AdsRandomResult(

    @SerializedName("adIdx")
    val adIdx: Int,

    @SerializedName("store")
    val store: String,

    @SerializedName("information")
    val information: String,

    @SerializedName("mainMenu")
    val mainMenu: String,

    @SerializedName("deliveryTips")
    val deliveryTips: Int,

    @SerializedName("location")
    val location: String,

    @SerializedName("request")
    val request: String,

    @SerializedName("createAt")
    val createAt: Timestamp,

    @SerializedName("updatedAt")
    val updatedAt: Timestamp,

    @SerializedName("status")
    val status: String,

    @SerializedName("userIdx")
    val userIdx: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("tid")
    val tid: String

)

