package umc.mobile.project.commercial.Auth.CommercialPost

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CommercialRecordRetrofitInterfaces {

    @POST("ad/{user_id}")
    fun sendPost(@Path("user_id") user_id: Int, record: CommercialRecord, file: MultipartBody.Part?) : Call<PostRecordResponse>
}