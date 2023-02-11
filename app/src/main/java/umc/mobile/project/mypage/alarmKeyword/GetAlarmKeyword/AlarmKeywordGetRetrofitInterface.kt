package umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface AlarmKeywordGetRetrofitInterface {
    @GET("user/{user_id}/keyword")
    fun getKeyword (@Path("user_id") userIdx: Int): Call<AlarmKeywordGetResponse>
}