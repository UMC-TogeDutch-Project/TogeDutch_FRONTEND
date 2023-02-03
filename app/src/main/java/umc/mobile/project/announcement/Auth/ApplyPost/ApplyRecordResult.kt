package umc.mobile.project.announcement.Auth.ApplyPost

interface ApplyRecordResult {
    fun applyRecordSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun applyRecordFailureMyAnnounce() // 실패했을 때 -> 나의공고일떄
    fun applyRecordFailureEnded() // 실패했을 때 -> 마감된 공고일때
    fun applyRecordFailure() // 실패했을 때 -> ”모집완료” or “공고사용불가한 공고”를 신청할때
}