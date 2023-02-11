package umc.mobile.project.wishlist.GetLikePost

import Post

interface LikePostGetResult {
    fun getPostUploadSuccess(code: Int, result: ArrayList<Post>)
    fun getPostUploadFailure(code : Int, message : String)
}