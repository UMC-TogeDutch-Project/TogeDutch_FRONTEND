package umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost

import umc.mobile.project.ram.chat.ChatPhoto

interface PostPhotoResult {
    fun sendPhotoSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun sendPhotoFailure() // 실패했을 때
}