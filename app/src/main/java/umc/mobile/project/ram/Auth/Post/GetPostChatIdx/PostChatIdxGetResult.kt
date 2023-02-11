package umc.mobile.project.ram.Auth.Post.GetPostChatIdx


import Post

interface PostChatIdxGetResult {
    fun getPostChatIdxSuccess(code: Int, result: Post)
    fun getPostChatIdxFailure(code : Int, message : String)
}