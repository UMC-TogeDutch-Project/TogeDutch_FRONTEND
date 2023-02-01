package umc.mobile.project.ram.Auth.Post.GetPostDetail

import Post


interface PostDetailGetResult {
    fun getPostUploadSuccess(code: Int, result: Post)
    fun getPostUploadFailure(code : Int, message : String)
}