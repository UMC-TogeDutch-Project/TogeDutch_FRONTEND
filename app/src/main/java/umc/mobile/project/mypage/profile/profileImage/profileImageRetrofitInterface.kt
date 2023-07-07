package umc.mobile.project.mypage.profile.profileImage

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface profileImageRetrofitInterface {
    @Multipart
    @PATCH("user/{user_id}/image")
    fun changeProfileImage(@Header("X-ACCESS-TOKEN") jwt: String, @Path("user_id") userIdx: Int, @Part profileImage: MultipartBody.Part) : Call<profileImagePatchResponse>
}