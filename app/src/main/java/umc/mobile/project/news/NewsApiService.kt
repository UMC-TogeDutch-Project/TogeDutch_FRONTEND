package umc.mobile.project.news

import android.app.Application
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import umc.mobile.project.news.mate.MateBtnData
import umc.mobile.project.news.mate.MatePostAcceptResponse
import umc.mobile.project.news.mate.MateResponse
import umc.mobile.project.news.upload.KeywordResponse
import umc.mobile.project.news.upload.UpLoadRequest
import umc.mobile.project.news.upload.UpLoadResponse


interface NewsApiService {

    //공고 업로드시 필요한 키워드(카테고리) 받아오기
    @GET("user/{user_id}/keyword")
    fun getKeyword(
        @Path("user_id") user_id : Int
    ) : Call<KeywordResponse>

    //위에서 받은 카테고리로 주변 공고 받아오기
    @POST("post/category")
    fun getPostFromCategory(
        @Body body: UpLoadRequest
//        @Query("category1") category1: String?,
//        @Query("category2") category2: String?,
//        @Query("category3") category3: String?,
//        @Query("category4") category4: String?,
//        @Query("category5") category5: String?,
//        @Query("category6") category6: String?,
//        @Query("latitude") latitude: Double,
//        @Query("longitude") longitude: Double
    ): Call<UpLoadResponse>


    //메이트 신청현황 받아오기
    @GET("application/waiting/{userIdx}")
    fun getMate(
        @Path("userIdx") userIdx : Int
    ) : Call<MateResponse>

    //공고 수락
    @POST("application/{applicationIdx}/accept")
    fun pushBtnAccept(
        @Path("applicationIdx") application : Int
    ) : Call<MatePostAcceptResponse>

    //공고 거절
    @POST("application/{applicationIdx}/deny")
    fun pushBtnDeny(
        @Path("applicationIdx") application : Int
    ) : Call<MatePostAcceptResponse>

}