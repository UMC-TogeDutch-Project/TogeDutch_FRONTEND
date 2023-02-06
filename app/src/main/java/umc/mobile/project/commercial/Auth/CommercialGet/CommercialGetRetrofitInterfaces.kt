package umc.mobile.project.commercial.Auth.CommercialGet

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CommercialGetRetrofitInterfaces {
    @GET("ad")
    fun sendPost(@Query("userIdx") user_id : Int) : Call<CommercialGetResponse>
}