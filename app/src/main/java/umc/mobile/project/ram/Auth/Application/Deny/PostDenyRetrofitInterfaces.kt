package umc.mobile.project.ram.Auth.Application.Deny

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostDenyRetrofitInterfaces {
    @Multipart
    @POST("application/{applicationIdx}/deny}")
    fun sendDeny(@Path("post") applicationIdx : Int) : Call<PostDenyResponse>
}