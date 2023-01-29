package umc.mobile.project.signup.Auth

import com.google.gson.annotations.SerializedName

data class SmsRequest(
    @SerializedName("to")
    val to: String?
)
