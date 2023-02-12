package umc.mobile.project.ram.chat

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ChatPhoto(
    @SerializedName(value =  "chatPhoto_id") var chatId : Int,
    @SerializedName(value =  "created_at") var createdAt : Timestamp,
    @SerializedName(value =  "chatRoom_id") var chatRoomId : Int,
    @SerializedName(value =  "user_id") var user_id : Int,
    @SerializedName(value =  "image") var image : String,
    var viewType : Int
)
