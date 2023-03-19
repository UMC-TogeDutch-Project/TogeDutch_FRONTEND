package umc.mobile.project.restaurant.Auth.NaverApi

sealed class NaverData{
    data class NaverSearchData(
        val title: String,
        val category: String,
        val description: String,
        val address: String
    ): NaverData()
    data class NaverImgData(
        val thumbnail: String
    ):NaverData()
    data class NaverTitle(
        val title: String
    ): NaverData()
}

