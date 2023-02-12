package umc.mobile.project.mypage.profile.Review

import umc.mobile.project.mypage.profile.ReviewGet

interface ReviewGetResult {
    fun getReviewSuccess(code: Int, result: ArrayList<ReviewGet>)
    fun getReviewFailure(code: Int, message: String)
}