package umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList

interface ChatRoomListGetResult {
    fun getChatRoomListSuccess(code: Int, result: ArrayList<ChatRoomList>)
    fun getChatRoomListFailure(code : Int, message : String)
}