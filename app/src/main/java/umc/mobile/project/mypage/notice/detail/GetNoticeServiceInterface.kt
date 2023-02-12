package umc.mobile.project.mypage.notice.detail

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetNoticeServiceInterface {
    @GET("notice/{noticeIdx}")
    fun getNoticeById(
        @Path("noticeIdx") noticeIdx: Int
    ): Call<GetNoticeResponse>
}