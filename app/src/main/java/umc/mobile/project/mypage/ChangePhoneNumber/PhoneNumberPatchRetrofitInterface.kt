package umc.mobile.project.mypage.ChangePhoneNumber

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PhoneNumberPatchRetrofitInterface {
    @PATCH("user/{user_id}/phone")
    fun changePhoneNumber(@Header("X-ACCESS-TOKEN") jwt: String, @Path("user_id") userIdx: Int, @Body phone: PhoneNumberPatchRequest) : Call<PhoneNumberPatchResponse>
}