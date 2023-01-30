package umc.mobile.project.signup.Auth

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import umc.mobile.project.signup.SignUpRequest
import umc.mobile.project.signup.SignUpResponse

interface ApiService {

    @Headers("Content-Type: multipart/form-data")
    @POST("user/signup")
    fun createNewUser(
        @Header("Content-Type: application/json")
        @Body body: SignUpRequest
//        @Part file: MultipartBody.Part? = null
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