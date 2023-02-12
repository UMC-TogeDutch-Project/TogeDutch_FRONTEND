package umc.mobile.project.commercial.Auth.RefundPost

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RefundRetrofitInterfaces {
    @POST("payment/refund")

    fun sendPost(@Query("tid") tid: String) : Call<RefundResponse>
}