package umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AlarmKeywordPatchRetrofitInterface {
    @PATCH("user/{user_id}/keyword")
    fun changeKeyword(@Header("X-ACCESS-TOKEN") jwt: String, @Path("user_id") userIdx: Int, @Body keyword: AlarmKeywordPatchRequest) : Call<AlarmKeywordPatchResponse>
}