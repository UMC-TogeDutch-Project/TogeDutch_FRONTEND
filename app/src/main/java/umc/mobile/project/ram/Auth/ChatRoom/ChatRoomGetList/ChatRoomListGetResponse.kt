package umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList

import com.google.gson.annotations.SerializedName


data class ChatRoomListGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<ChatRoomList>
)
