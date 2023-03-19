package umc.mobile.project.ram.Auth.Chat.ChatMember

import com.google.gson.annotations.SerializedName
import umc.mobile.project.ram.chat.Chat


data class ChatMemberGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<ChatMember>
)

data class ChatMember(
    @SerializedName(value = "userName") val userName : String,
    @SerializedName(value = "member") val member : Int
)
