package umc.mobile.project.ram.Auth.Matching.GetMatchingAccept

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchingAcceptGetRetrofitInterface {
    @GET("matching/wait/{post_id}/{user_id}")
    fun getMatchingAccept (@Path("post_id") post_id: Int, @Path("user_id") user_id: Int): Call<MatchingAcceptGetResponse>
}