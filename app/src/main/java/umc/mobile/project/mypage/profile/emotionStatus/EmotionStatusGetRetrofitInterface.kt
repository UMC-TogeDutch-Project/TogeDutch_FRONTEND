package umc.mobile.project.mypage.profile.emotionStatus

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EmotionStatusGetRetrofitInterface {
    @GET("review/emotion/{post_id}")
    fun getJoinApplication (@Path("post_id") post_id: Int): Call<EmotionStatusGetResponse>
}