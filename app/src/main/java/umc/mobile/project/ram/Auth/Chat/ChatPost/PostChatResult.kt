package umc.mobile.project.ram.Auth.Chat.ChatPost

interface PostChatResult {
    fun sendChatSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun sendChatFailure() // 실패했을 때
}