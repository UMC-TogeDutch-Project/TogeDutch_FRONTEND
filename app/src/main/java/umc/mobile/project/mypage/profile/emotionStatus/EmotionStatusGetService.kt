package umc.mobile.project.mypage.profile.emotionStatus

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class EmotionStatusGetService {
    private lateinit var emotionStatusGetResult: EmotionStatusGetResult

    fun setEmotionStatusGetResult(emotionStatusGetResult: EmotionStatusGetResult) {
        this.emotionStatusGetResult = emotionStatusGetResult
    }

    fun getEmotionStatus(post_id: Int) {
        val apiGetService = getRetrofit().create(EmotionStatusGetRetrofitInterface::class.java)

        apiGetService.getJoinApplication(post_id).enqueue(object : Callback<EmotionStatusGetResponse> {
            override fun onResponse(call: Call<EmotionStatusGetResponse>, response: Response<EmotionStatusGetResponse>, ) {
                Log.d("EMOTIONSTATUS-GET SUCCESS", response.toString())
                val resp: EmotionStatusGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when (resp.code) {
                    1000 -> emotionStatusGetResult.getEmotionStatusSuccess(resp.code, resp.result!!)
                    else -> emotionStatusGetResult.getEmotionStatusFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<EmotionStatusGetResponse>, t: Throwable) {
                Log.d("EMOTIONSTATUS-GET FAILURE", t.message.toString())
            }
        })
    }
}
