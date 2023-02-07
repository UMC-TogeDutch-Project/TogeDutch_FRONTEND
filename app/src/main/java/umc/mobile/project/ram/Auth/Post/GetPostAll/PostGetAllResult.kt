package umc.mobile.project.ram.Auth.Post.GetPostAll

import Post

interface PostGetAllResult {
    fun getPostAllSuccess(code: Int, result: ArrayList<Post>)
    fun getPostAllFailure(code : Int, message : String)
}