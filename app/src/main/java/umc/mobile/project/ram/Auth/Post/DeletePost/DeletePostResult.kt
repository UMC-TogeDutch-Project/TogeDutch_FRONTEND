package umc.mobile.project.ram.Auth.Post.DeletePost

interface DeletePostResult {
    fun deletePostSuccess(result : Int) // 성공했을 때, Result는 ~Response 정의한 data class
    fun deletePostFailure() // 실패했을 때
}