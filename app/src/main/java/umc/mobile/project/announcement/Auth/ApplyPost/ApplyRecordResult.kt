package umc.mobile.project.announcement.Auth.ApplyPost

interface ApplyRecordResult {
    fun applyRecordSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun applyRecordFailure() // 실패했을 때
}