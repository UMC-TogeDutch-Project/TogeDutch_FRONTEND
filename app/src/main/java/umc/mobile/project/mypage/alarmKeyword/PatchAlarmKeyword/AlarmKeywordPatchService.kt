package umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class AlarmKeywordPatchService {
    var timestamp = Timestamp(Date().time)
    //var timestamp = Date(System.currentTimeMillis())
    private var result : ChangeKeywordResult = ChangeKeywordResult(keywordIdx = 1, word1 =  "", word2 = "",
        word3 = "", word4 = "", word5 = "", word6 = "" )

    private lateinit var alarmKeywordPatchResult: AlarmKeywordPatchResult

    fun setKeywordPatchResult(alarmKeywordPatchResult: AlarmKeywordPatchResult){
        this.alarmKeywordPatchResult = alarmKeywordPatchResult
    }

    fun changeKeyword(jwt : String, userIdx : Int, keyword : AlarmKeywordPatchRequest){
        val authService = getRetrofit().create(AlarmKeywordPatchRetrofitInterface::class.java)
        authService.changeKeyword(jwt, userIdx, keyword).enqueue(object: Callback<AlarmKeywordPatchResponse> {
            override fun onResponse(call: Call<AlarmKeywordPatchResponse>, response: Response<AlarmKeywordPatchResponse>) {
                Log.d("CHANGE-KEYWORD-GET SUCCESS",response.toString())
                val resp: AlarmKeywordPatchResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                result = resp.result!!
                when(resp.code){
                    1000 -> alarmKeywordPatchResult.changeKeywordSuccess(result)
                    else -> alarmKeywordPatchResult.changeKeywordFailure()
                }
            }

            override fun onFailure(call: Call<AlarmKeywordPatchResponse>, t: Throwable) {
                Log.d("CHANGE-KEYWORD-GET SUCCESS",t.message.toString())
            }
        })
    }
}