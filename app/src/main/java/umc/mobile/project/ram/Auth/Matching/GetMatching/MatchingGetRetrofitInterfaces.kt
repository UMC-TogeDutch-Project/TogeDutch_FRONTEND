package umc.mobile.project.ram.Auth.Matching.GetMatching

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchingGetRetrofitInterfaces {
    @GET("matching/rematching/{post_id}/")
    fun getMatching (@Path("post_id") post_id: Int): Call<MatchingGetResponse>
}