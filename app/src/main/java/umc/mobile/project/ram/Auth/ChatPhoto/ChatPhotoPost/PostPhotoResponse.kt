package umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostChatResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "chatPhoto_id") var chatId : Int,
    @SerializedName(value =  "createAt") var createdAt : Timestamp,
    @SerializedName(value =  "chatRoom_id") var chatRoomId : Int,
    @SerializedName(value =  "user_id") var user_id : Int,
    @SerializedName(value =  "image") var image : String
)

