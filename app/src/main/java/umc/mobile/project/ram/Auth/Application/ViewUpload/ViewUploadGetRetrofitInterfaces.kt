package umc.mobile.project.ram.Auth.Application.ViewUpload


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ViewUploadGetRetrofitInterfaces {
//    @GET("post/{post_id}/")
    @GET("user/{user_id}/application/upload")
    fun getViewUpload (@Path("user_id") user_id: Int): Call<ViewUploadGetResponse>
}