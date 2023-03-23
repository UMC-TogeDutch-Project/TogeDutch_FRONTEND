package umc.mobile.project.ram.Auth.Declaration.DeclarationPost

import retrofit2.Call
import retrofit2.http.*

interface PostDeclarationRetrofitInterfaces {
    @POST("chatRoom/{chatRoom_id}/declaration")
    fun sendDeclaration(@Path("chatRoom_id") chatRoom_id : Int, @Body content : declarationPost) : Call<PostDeclarationResponse>
}