package umc.mobile.project.withdrawal;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;


public interface WithdrawalServiceRetrofitInterface {
    @PATCH("user/{user_id}/status?status=inactive")
    fun changeUserStatus(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Path("user_id") userIdx: Int
    ): Call<WithdrawalResponse>
}
