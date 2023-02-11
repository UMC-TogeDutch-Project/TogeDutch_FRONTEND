package umc.mobile.project.ram.Auth.Post.DeletePost

import retrofit2.Call
import retrofit2.http.*

interface DeletePostRetrofitInterfaces {
    @DELETE("post/delete/{postIdx}")
    fun deletePost(@Path("postIdx") post_id : Int) : Call<DeletePostResponse>
}