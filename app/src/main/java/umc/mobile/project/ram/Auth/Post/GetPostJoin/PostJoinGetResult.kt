package umc.mobile.project.ram.Auth.Post.GetPostJoin


import Post

interface PostJoinGetResult {
    fun getPostJoinSuccess(code: Int, result: ArrayList<Post>)
    fun getPostJoinFailure(code : Int, message : String)
}