package umc.mobile.project.ram.Auth.Application.ViewUpload

import Post

interface ViewUploadGetResult {
    fun getPostUploadSuccess(code: Int, result: ArrayList<ApplicationGet>)
    fun getPostUploadFailure(code : Int, message : String)
}