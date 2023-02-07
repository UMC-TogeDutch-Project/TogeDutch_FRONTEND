package umc.mobile.project.signup

import com.google.gson.annotations.SerializedName

data class SmsRequest(
    @SerializedName("to")
    val to: String
)
