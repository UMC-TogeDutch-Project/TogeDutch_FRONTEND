package umc.mobile.project.ram.Auth.Post.GetPost

import Post

interface PostGetResult {
    fun getPostSuccess(code: Int, result: Post)
    fun getPostFailure(code : Int, message : String)
}