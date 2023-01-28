package umc.mobile.project.signup.Auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import umc.mobile.project.signup.SignUpRequest
import umc.mobile.project.signup.SignUpResponse

interface ApiService {
    @POST("user/signup")
    fun createNewUser(
        @Body body: SignUpRequest
    ): Call<SignUpResponse>

    @POST("user/keyword")
    fun getUserKeywordId(
        @Body body: KeywordRequest
    ):Call<KeywordResponse>
}