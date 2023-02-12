package umc.mobile.project.wishlist.LikeDelete



interface LikeDeleteResult {


    fun LikeDeleteSuccess() // 성공했을 때, Result는 ~Response 정의한 data class
    fun LikeDeleteFailure()
// 실패했을 때 -> ”모집완료” or “공고사용불가한 공고”를 신청할때
}