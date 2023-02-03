package umc.mobile.project.commercial.Auth.CommercialPost

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CommercialRecordRetrofitInterfaces {
    @Multipart
    @POST("ad")
    fun sendPost(@Path("user_id") user_id: Int, @Part("ad")record: CommercialRecord, @Part file: MultipartBody.Part?) : Call<PostRecordResponse>
}