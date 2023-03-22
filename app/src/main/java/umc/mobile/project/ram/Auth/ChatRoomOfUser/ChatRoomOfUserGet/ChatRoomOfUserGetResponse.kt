package umc.mobile.project.ram.Auth.ChatRoomOfUser.ChatRoomOfUserGet

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


data class ChatMemberGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<ChatMember>
)

data class ChatMember(
    @SerializedName(value = "chatRoom_id") val chatRoom_id : Int,
    @SerializedName(value = "user_id") val user_id : Int,
    @SerializedName(value = "status") val status : Int,
    @SerializedName(value = "is_read") val is_read : Int,
    @SerializedName(value = "updated_at") val updated_at : Timestamp,
    @SerializedName(value = "userName") val userName : String,
)
