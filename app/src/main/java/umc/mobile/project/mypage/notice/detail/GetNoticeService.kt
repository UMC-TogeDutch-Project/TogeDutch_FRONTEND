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
                Log.d("NOTICEDETAIL-GET SUCCESS",response.toString())
                val resp: GetNoticeResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
                    1000 -> getNoticeResult.getNoticeSuccess(resp.code, resp.result!!)
                    else -> getNoticeResult.getNoticeFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<GetNoticeResponse>, t: Throwable) {
                Log.d("NOTICEDETAIL-GET FAILURE",t.message.toString())
            }
        })
    }
}