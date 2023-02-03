package umc.mobile.project.mypage.GetUser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserGetRetrofitInterface {
    @GET("user/{user_id}/")
    fun getUser (@Path("user_id") userIdx: Int): Call<UserGetResponse>
}