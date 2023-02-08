package umc.mobile.project.mypage.ChangePassword

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PasswordPatchRetrofitInterface {
    @PATCH("user/{user_id}/password")
    fun changePassword(@Header("X-ACCESS-TOKEN") jwt: String, @Path("user_id") userIdx: Int, @Body password: PasswordPatchRequest) : Call<PasswordPatchResponse>
}