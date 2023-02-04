package umc.mobile.project.signup

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsApiService {

    @POST("sms/send")
    fun sendCheckNum(
        @Body to : SmsRequest
    ): Call<SmsResponse>

}