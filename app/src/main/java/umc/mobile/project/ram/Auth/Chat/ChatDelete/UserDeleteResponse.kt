package umc.mobile.project.ram.Auth.Chat.ChatDelete

import com.google.gson.annotations.SerializedName


data class UserDeleteResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Int
)
