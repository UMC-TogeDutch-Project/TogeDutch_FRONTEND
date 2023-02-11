package umc.mobile.project.ram.Auth.Matching.GetMatchingAccept

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchingAcceptGetRetrofitInterface {
    @GET("matching/wait/{user_id}/{post_id}")
    fun getMatchingAccept (@Path("user_id") user_id: Int, @Path("post_id") post_id: Int): Call<MatchingAcceptGetResponse>
}