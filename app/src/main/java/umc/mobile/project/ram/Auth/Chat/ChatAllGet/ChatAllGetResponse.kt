package umc.mobile.project.ram.Auth.Chat.ChatAllGet

import com.google.gson.annotations.SerializedName
import umc.mobile.project.ram.chat.Chat


data class ChatAllGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<Chat>
)
