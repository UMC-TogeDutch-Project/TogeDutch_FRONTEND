package umc.mobile.project.login

import com.google.gson.annotations.SerializedName

data class FindPwdResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: String
)
