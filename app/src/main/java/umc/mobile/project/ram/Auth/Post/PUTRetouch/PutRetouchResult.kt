package umc.mobile.project.ram.Auth.Post.PUTRetouch

import Post

interface PutRetouchResult {
    fun PutRetouchSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun PutRetouchFailure() // 실패했을 때
}