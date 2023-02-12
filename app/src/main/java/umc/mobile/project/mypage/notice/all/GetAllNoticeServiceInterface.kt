package umc.mobile.project.mypage.notice.all

import retrofit2.Call
import retrofit2.http.GET

interface GetAllNoticeServiceInterface {
    @GET("notice/?sort=latest")
    fun getAllNotices(): Call<GetAllNoticeResponse>
}