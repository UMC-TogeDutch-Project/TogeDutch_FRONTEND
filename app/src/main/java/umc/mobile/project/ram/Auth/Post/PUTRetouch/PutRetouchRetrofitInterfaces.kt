package umc.mobile.project.ram.Auth.Post.PUTRetouch

import retrofit2.Call
import retrofit2.http.*

interface PutRetouchRetrofitInterfaces {
    @POST("post/{post_id}")
    fun putRetouch(@Path("post_id") post_id : Int, @Query("user") user_id : Int, title : String, url : String, delivery_tips : Int, minmum : Int, order_time : String,
    num_of_recruits : Int, recruited_num : Int, status : String, latitude : Double, longitude : Double ) : Call<PutRetouchResponse>
}