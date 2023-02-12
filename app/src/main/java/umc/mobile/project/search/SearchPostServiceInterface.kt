package umc.mobile.project.search

import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Query

interface SearchPostServiceInterface {
    @PATCH("post/search/")
    fun searchPostByKeyword(
        @Query("keyword") keyword: String
    ): Call<SearchPostResponse>
}