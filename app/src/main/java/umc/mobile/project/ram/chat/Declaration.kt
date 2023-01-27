package umc.mobile.project.ram.chat

data class Declaration(
    var declaration_id : Int,
    var content : String,
    var created_at : String,
    var status : String,
    var chatRoom_id : Int
)
