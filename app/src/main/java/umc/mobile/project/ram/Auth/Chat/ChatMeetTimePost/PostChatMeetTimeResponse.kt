package umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostChatMeetTimeResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ChatMeetTime?
)

data class ChatMeetTime(
    @SerializedName(value =  "chatMeetTimeId") var chatMeetTimeId : Int,
    @SerializedName(value =  "chatRoomId") var chatRoomId : Int,
    @SerializedName(value =  "userId") var userId : Int,
    @SerializedName(value =  "meetTime") var meetTime : String,
    @SerializedName(value =  "createdAt") var createdAt : Timestamp
)


