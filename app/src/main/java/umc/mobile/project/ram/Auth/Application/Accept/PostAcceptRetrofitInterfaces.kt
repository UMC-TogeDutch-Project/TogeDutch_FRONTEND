package umc.mobile.project.ram.Auth.Application.Accept

import retrofit2.Call
import retrofit2.http.*

interface PostAcceptRetrofitInterfaces {
    @Multipart
    @POST("application/{applicationIdx}/accept}")
    fun sendAccept(@Path("post") applicationIdx : Int) : Call<PostAcceptResponse>
}