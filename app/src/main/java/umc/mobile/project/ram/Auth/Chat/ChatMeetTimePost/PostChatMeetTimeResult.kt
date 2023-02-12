package umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost

interface PostChatMeetTimeResult {
    fun sendChatMeetTimeSuccess(result: ChatMeetTime) // 성공했을 때, Result는 ~Response 정의한 data class
    fun sendChatMeetTimeFailure() // 실패했을 때
}