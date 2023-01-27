package umc.mobile.project.ram.chat

data class ChatPhoto(
    var chatPhoto_id : Int,
    var created_at : String,
    var chatRoom_id : Int,
    var user_id : Int,
    var file_id : Int
)
