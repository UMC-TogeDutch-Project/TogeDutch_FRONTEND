package umc.mobile.project.ram.Auth.ChatLocation

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.math.BigDecimal

interface PostLocationRetrofitInterfaces {
    @POST("chatRoom/{chatRoom_id}/chatLocation")
    fun sendLocation(@Path("chatRoom_id") chatRoom_id : Int, @Query("user") user : Int, @Query("latitude") latitude : BigDecimal, @Query("longitude") longitude : BigDecimal ) : Call<PostLocationResponse>
}