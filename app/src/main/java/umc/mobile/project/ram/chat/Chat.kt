package umc.mobile.project.ram.chat

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Chat(
    @SerializedName(value =  "chat_id") var chat_id : Int,
    @SerializedName(value =  "chatRoom_id") var chatRoom_id : Int,
    @SerializedName(value =  "user_id") var user_id : Int,
    @SerializedName(value =  "created_at") var created_at : Timestamp,
    @SerializedName(value =  "content") var content : String,
    @SerializedName(value =  "writer") var writer : String,
    @SerializedName(value = "type") var type : String,
    var viewType : Int
)
