package umc.mobile.project.ram.Auth.ChatRoomOfUser.IsReadPut


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface IsReadPutRetrofitInterfaces {
    @PUT("chatRoom/{chatRoom_id}/user/{user_id}/in")
    fun putIsRead (@Path("chatRoom_id") chatRoom_id: Int, @Path("user_id") user_id : Int): Call<IsReadPutResponse>
}