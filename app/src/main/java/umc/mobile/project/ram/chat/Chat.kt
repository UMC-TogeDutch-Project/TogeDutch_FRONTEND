package umc.mobile.project.ram.chat

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Chat(
    @SerializedName(value =  "chatId") var chat_id : Int,
    @SerializedName(value =  "chatRoomId") var chatRoom_id : Int,
    @SerializedName(value =  "userId") var user_id : Int,
    @SerializedName(value =  "createAt") var created_at : Timestamp,
    @SerializedName(value =  "content") var content : String,
    @SerializedName(value =  "writer") var writer : String,
    @SerializedName(value = "type") var type : String,
    var viewType : Int
)
