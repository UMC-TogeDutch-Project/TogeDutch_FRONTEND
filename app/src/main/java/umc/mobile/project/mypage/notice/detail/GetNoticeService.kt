package umc.mobile.project.mypage.notice.detail

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class GetNoticeService {
    var timestamp = Timestamp(Date().time)

    private lateinit var getNoticeResult : GetNoticeResult

    fun setGetNoticeResult(getNoticeResult: GetNoticeResult){
        this.getNoticeResult = getNoticeResult
    }

    fun getNoticeById(noticeIdx: Int){
        val getNoticeService = getRetrofit().create(GetNoticeServiceInterface::class.java)
        getNoticeService.getNoticeById(noticeIdx).enqueue(object: Callback<GetNoticeResponse> {
            override fun onResponse(call: Call<GetNoticeResponse>, response: Response<GetNoticeResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val getNoticeResponse: GetNoticeResponse = response.body()!!

                when(getNoticeResponse.code) {
                    1000 -> getNoticeResult.getNoticeSuccess(getNoticeResponse.result)
                    else -> getNoticeResult.getNoticeFailure()
                }
            }

            override fun onFailure(call: Call<GetNoticeResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}