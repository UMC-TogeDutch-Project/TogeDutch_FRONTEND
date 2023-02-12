package umc.mobile.project.mypage.notice.detail

import umc.mobile.project.mypage.notice.detail.Notice

interface GetNoticeResult {
    fun getNoticeSuccess(result: Notice)
    fun getNoticeFailure()
}