package umc.mobile.project.restaurant.Auth.PlaceApi

interface PlaceGetResult {
    fun getPostSuccess(result: ArrayList<umc.mobile.project.restaurant.Auth.PlaceApi.Place>)
    fun getPostFailure()
}