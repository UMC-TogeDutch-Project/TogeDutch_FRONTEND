package umc.mobile.project.ram.Auth.Post.PUTPost

interface PutPostResult {
    fun PutPostSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun PutPostFailure() // 실패했을 때
}