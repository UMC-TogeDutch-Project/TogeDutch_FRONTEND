package umc.mobile.project.mypage.profile.Review

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewGetRetrofitInterface {
    @GET("review/{post_id}")
    fun getReview(
        @Path("post_id") post_id: Int
    ): Call<ReviewGetResponse>
}