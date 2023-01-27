package umc.mobile.project.signup

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("user/signup")
    fun createNewUser(
        @Body body: Request
    ): Call<Response>
}