package umc.mobile.project.commercial.Auth.CommercialDetailGet


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommercialDetailGetRetrofitInterfaces {
    @GET("ad/{ad_id}/")
    fun getPostDetail (@Path("ad_id") ad_id: Int): Call<CommercialDetailGetResponse>
}