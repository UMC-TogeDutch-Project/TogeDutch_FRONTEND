package umc.mobile.project.mypage.notice.all

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class GetAllNoticeService {
    var timestamp = Timestamp(Date().time)

    private lateinit var getAllNoticeResult : GetAllNoticeResult

    fun setGetAllNoticeResult(getAllNoticeResult: GetAllNoticeResult){
        this.getAllNoticeResult = getAllNoticeResult
    }

    fun getAllNotices(){
        val getNoticesService = getRetrofit().create(GetAllNoticeServiceInterface::class.java)
        getNoticesService.getAllNotices().enqueue(object: Callback<GetAllNoticeResponse> {
            override fun onResponse(call: Call<GetAllNoticeResponse>, response: Response<GetAllNoticeResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val getAllNoticeResponse: GetAllNoticeResponse = response.body()!!

                when(getAllNoticeResponse.code) {
                    1000 -> getAllNoticeResult.getAllNoticesSuccess(getAllNoticeResponse.result)
                    else -> getAllNoticeResult.getAllNoticesFailure()
                }
            }

            override fun onFailure(call: Call<GetAllNoticeResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}