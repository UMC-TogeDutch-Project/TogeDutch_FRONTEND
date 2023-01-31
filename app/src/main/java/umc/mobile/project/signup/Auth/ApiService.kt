package umc.mobile.project.signup.Auth

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import umc.mobile.project.signup.SignUpRequest
import umc.mobile.project.signup.SignUpResponse

interface ApiService {

    @Multipart
    @POST("user/signup")
    fun createNewUser(
        @Part ("user") user : SignUpRequest,
        @Part file: MultipartBody.Part? = null
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