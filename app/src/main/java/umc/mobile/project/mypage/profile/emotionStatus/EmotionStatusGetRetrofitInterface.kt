package umc.mobile.project.mypage.profile.emotionStatus

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EmotionStatusGetRetrofitInterface {
    @GET("review/uploaduser/{user_id}")
    fun getJoinApplication (@Path("user_id") user_id: Int): Call<EmotionStatusGetResponse>
}