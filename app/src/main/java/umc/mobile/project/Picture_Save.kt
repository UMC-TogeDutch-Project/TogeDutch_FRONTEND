package umc.mobile.project

import okhttp3.MultipartBody

data class Picture_Save (
    var post_id : Int,
    var file_name : String,
    var file : MultipartBody.Part
)