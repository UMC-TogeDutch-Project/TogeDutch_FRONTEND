package umc.mobile.project.wishlist.LikeDelete



interface LikeDeleteResult {
    fun getLikeDeleteSuccess(result : Int) // 성공했을 때, Result는 ~Response 정의한 data class
    fun getLikeDeleteFailure(result : Int)
}