package umc.mobile.project.mypage.notice.detail

import umc.mobile.project.mypage.notice.NoticeGet


interface GetNoticeResult {
    fun getNoticeSuccess(code: Int, result: NoticeGet)
    fun getNoticeFailure(code : Int, message : String)
}