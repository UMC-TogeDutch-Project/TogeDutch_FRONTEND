package umc.mobile.project.mypage.notice.all

interface GetAllNoticeResult {
    fun getAllNoticesSuccess(result: ArrayList<Notice>)
    fun getAllNoticesFailure()
}