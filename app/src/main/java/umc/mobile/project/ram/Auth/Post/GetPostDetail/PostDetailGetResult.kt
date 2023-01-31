package umc.mobile.project.ram.Auth.Post.GetPostDetail

import umc.mobile.project.Post


interface PostDetailGetResult {
    fun getPostUploadSuccess(code: Int, result: Post)
    fun getPostUploadFailure(code : Int, message : String)
}