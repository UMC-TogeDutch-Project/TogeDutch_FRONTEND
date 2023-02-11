package umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit


class AlarmKeywordGetService {
    private lateinit var alarmKeywordResult: AlarmKeywordGetResult

    fun setKeywordGetResult(alarmKeywordResult: AlarmKeywordGetResult){
        this.alarmKeywordResult = alarmKeywordResult
    }

    fun getKeyword(userIdx : Int){
        val keywordGetService = getRetrofit().create(AlarmKeywordGetRetrofitInterface::class.java)

        keywordGetService.getKeyword(userIdx).enqueue(object : Callback<AlarmKeywordGetResponse> {
            override fun onResponse(call: Call<AlarmKeywordGetResponse>, response: Response<AlarmKeywordGetResponse>,) {
                Log.d("KEYWORD-GET SUCCESS",response.toString())
                val resp : AlarmKeywordGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
//                    1000 ->
                    1000 -> alarmKeywordResult.getKeywordSuccess(resp.code, resp.result!!)
                    else -> alarmKeywordResult.getKeywordFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AlarmKeywordGetResponse>, t: Throwable) {
                Log.d("KEYWORD-GET FAILURE",t.message.toString())
            }
        })
    }
}