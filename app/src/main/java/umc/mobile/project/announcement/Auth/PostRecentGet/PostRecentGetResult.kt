package umc.mobile.project.announcement.Auth.PostRecentGet

import Post

interface PostRecentGetResult {
    fun recordSuccess(result: ArrayList<Post>) // 성공했을 때, Result는 ~Response 정의한 data class
    fun recordFailure() // 실패했을 때
}