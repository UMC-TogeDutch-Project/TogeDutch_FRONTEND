package umc.mobile.project.announcement.Auth.ApplyPost

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApplyRecordRetrofitInterfaces {
    @POST("post/{postIdx}/application")
    fun sendPost(@Path("postIdx")postIdx : Int) : Call<ApplyRecordResponse>
}