package umc.mobile.project.announcement.Auth.ApplyPost

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApplyRecordRetrofitInterfaces {

    @POST("post/{postIdx}/application")
    fun sendPost( @Header("X-ACCESS-TOKEN")x_access_token: String, @Path("postIdx")postIdx : Int) : Call<ApplyRecordResponse>
}