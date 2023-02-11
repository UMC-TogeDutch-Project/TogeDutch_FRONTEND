package umc.mobile.project.ram.Auth.Application.Join

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JoinApplicationGetRetrofitInterface {
    @GET("user/{user_id}/application/join")
    fun getJoinApplication (@Path("user_id") user_id: Int): Call<JoinApplicationGetResponse>
}