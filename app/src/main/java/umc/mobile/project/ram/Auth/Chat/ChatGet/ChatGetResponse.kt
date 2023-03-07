package umc.mobile.project.ram.Auth.Chat.ChatGet

import com.google.gson.annotations.SerializedName
import umc.mobile.project.ram.chat.Chat


data class ChatGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Chat
)
