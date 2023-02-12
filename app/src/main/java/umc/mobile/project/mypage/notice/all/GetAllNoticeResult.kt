package umc.mobile.project.mypage.notice.all

import umc.mobile.project.mypage.notice.NoticeGet

interface GetAllNoticeResult {
    fun getAllNoticesSuccess(code: Int, result: ArrayList<NoticeGet>)
    fun getAllNoticesFailure(code : Int, message : String)
}