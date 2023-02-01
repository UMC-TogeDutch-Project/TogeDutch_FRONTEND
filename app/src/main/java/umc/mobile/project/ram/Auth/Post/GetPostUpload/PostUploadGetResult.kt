package umc.mobile.project.ram.Auth.Post.GetPostUpload

import Post

interface PostUploadGetResult {
    fun getPostUploadSuccess(code: Int, result: ArrayList<Post>)
    fun getPostUploadFailure(code : Int, message : String)
}