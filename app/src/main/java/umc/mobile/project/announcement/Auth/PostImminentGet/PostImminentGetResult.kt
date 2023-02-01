package umc.mobile.project.announcement.Auth.PostImminentGet

import Post

interface PostImminentGetResult {
    fun recordSuccess1(result: ArrayList<Post>) // 성공했을 때, Result는 ~Response 정의한 data class
    fun recordFailure1() // 실패했을 때
}