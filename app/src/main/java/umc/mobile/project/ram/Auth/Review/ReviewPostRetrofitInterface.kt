package umc.mobile.project.ram.Auth.Review

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewPostRetrofitInterface {
    @POST("review/{application_id}")
    fun sendReview(@Path("application_id") application_id: Int, @Body review: ReviewPostRequest) : Call<ReviewPostResponse>
}