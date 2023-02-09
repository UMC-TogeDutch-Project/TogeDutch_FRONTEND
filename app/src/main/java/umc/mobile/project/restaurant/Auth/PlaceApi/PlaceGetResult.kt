package umc.mobile.project.restaurant.Auth.PlaceApi

import Post

interface PlaceGetResult {
    fun getPostSuccess(code: Int, result: Post)
    fun getPostFailure(code : Int, message : String)
}