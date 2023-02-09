package umc.mobile.project.wishlist.GetLikePost

import umc.mobile.project.LikePost

interface LikePostGetResult {
    fun getPostUploadSuccess(code: Int, result: ArrayList<LikePost>)
    fun getPostUploadFailure(code : Int, message : String)
}