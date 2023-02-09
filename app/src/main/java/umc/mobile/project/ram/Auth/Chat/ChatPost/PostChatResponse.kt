package umc.mobile.project.ram.Auth.Chat.ChatPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostChatResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "chatId") var chatId : Int,
    @SerializedName(value =  "chatRoomId") var chatRoomId : Int,
    @SerializedName(value =  "userId") var userId : Int,
    @SerializedName(value =  "createdAt") var createdAt : Timestamp,
    @SerializedName(value =  "content") var content : String,
    @SerializedName(value =  "writer") var writer : String,
    @SerializedName(value = "type") var type : String
)

data class chatPost(
    @SerializedName(value =  "content") var content : String,
)
