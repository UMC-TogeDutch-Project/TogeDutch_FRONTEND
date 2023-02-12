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
                Log.d("NOTICE-GET SUCCESS",response.toString())
                val resp: GetAllNoticeResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
                    1000 -> getAllNoticeResult.getAllNoticesSuccess(resp.code, resp.result!!)
                    else -> getAllNoticeResult.getAllNoticesFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<GetAllNoticeResponse>, t: Throwable) {
                Log.d("NOTICE-GET FAILURE",t.message.toString())
            }
        })
    }
}