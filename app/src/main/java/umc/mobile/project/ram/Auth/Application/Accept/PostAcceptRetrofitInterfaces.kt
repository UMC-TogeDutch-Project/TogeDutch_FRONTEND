package umc.mobile.project.ram.Auth.Application.Accept

import retrofit2.Call
import retrofit2.http.*

interface PostAcceptRetrofitInterfaces {
    @POST("application/{applicationIdx}/accept")
    fun sendAccept(@Path("applicationIdx") applicationIdx : Int) : Call<PostAcceptResponse>
}