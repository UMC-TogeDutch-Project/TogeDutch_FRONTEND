package umc.mobile.project.ram.Auth.Post.PUTPostStatus

interface PutPostStatusResult {
    fun PutPostStatusSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun PutPostStatusFailure() // 실패했을 때
}