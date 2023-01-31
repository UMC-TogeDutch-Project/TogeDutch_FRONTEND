package umc.mobile.project.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLoginService {

    @POST("user/login")
    fun userLogin(
        @Body user : LoginRequest
    ): Call<LoginResponse>

}