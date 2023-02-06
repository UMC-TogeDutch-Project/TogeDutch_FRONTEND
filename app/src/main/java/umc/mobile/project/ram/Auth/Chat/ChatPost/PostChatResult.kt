package umc.mobile.project.ram.Auth.Chat.ChatPost

interface PostChatResult {
    fun AcceptSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun AcceptFailure() // 실패했을 때
}