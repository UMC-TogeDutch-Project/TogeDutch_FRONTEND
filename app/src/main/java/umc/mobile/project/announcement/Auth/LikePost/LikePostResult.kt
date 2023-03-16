package umc.mobile.project.announcement.Auth.LikePost



interface LikePostResult {
    fun LikePostSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun LikePostFailureMyPost()
    fun LikePostFailureAdd()// 실패했을 때 -> ”모집완료” or “공고사용불가한 공고”를 신청할때
}