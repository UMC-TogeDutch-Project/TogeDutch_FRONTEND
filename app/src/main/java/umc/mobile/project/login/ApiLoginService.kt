package umc.mobile.project.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiLoginService {

    @POST("user/login")
    fun userLogin(
        @Body user : LoginRequest
    ): Call<LoginResponse>

    @GET("user/findPwd")
    fun findPwd(
        @Query ("email") email : String
    ): Call<FindPwdResponse>

    @POST("oauth/kakao")
    fun kakaoLogin(
            @Query("email") email: String?
    ): Call<LoginResponse>
}