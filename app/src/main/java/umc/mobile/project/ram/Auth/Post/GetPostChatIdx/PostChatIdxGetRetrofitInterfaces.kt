package umc.mobile.project.ram.Auth.Post.GetPostChatIdx


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostChatIdxGetRetrofitInterfaces {
    @GET("post/chatRoom/{chatRoomIdx}")
    fun getPostChatIdx (@Path("chatRoomIdx") chatRoomIdx: Int): Call<PostChatIdxGetResponse>
}