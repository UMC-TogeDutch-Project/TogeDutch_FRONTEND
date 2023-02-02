package umc.mobile.project.ram.Auth.Application.GetUser

import com.google.gson.annotations.SerializedName


data class UserGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : UserGet
)
