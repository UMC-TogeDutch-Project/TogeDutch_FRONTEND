package umc.mobile.project.ram.Auth.Chat.ChatDelete

interface ChatRoomDeleteResult {
    fun chatRoomDeleteSuccess(code: Int, result: Int)
    fun chatRoomDeleteFailure(code : Int, message : String)
}