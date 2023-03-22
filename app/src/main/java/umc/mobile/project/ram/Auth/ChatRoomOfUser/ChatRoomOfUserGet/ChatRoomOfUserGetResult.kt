package umc.mobile.project.ram.Auth.ChatRoomOfUser.ChatRoomOfUserGet

interface ChatRoomOfUserGetResult {
    fun getChatMemberSuccess(code: Int, result: ArrayList<ChatMember>)
    fun getChatMemberFailure(code : Int, message : String)
}