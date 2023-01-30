package umc.mobile.project.announcement.Auth.PostPost

interface PostRecordResult {
    fun recordSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun recordFailure() // 실패했을 때
}