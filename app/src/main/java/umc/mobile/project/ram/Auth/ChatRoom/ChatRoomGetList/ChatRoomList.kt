package umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ChatRoomList(
    @SerializedName(value =  "chatRoomIdx") val chatRoomIdx : Int,
    @SerializedName(value =  "createdAt") val createdAt : Timestamp
)
