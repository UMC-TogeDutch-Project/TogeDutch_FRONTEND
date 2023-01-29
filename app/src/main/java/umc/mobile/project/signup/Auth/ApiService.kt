package umc.mobile.project.signup.Auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import umc.mobile.project.signup.SignUpRequest
import umc.mobile.project.signup.SignUpResponse

interface ApiService {
    @POST("user/signup")
    fun createNewUser(
        @Body body: SignUpRequest
    ): Call<SignUpResponse>

    @POST("sms/send")
    fun sendCheckNum(
        @Body body: SmsRequest
    ): Call<SmsResponse>

    @POST("user/keyword")
    fun getUserKeywordId(
        @Body body: KeywordRequest
    ):Call<KeywordResponse>
}