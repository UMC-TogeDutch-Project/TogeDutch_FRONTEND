package umc.mobile.project.commercial.Auth.CommercialDetailGet

import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGet

interface CommercialDetailGetResult {
    fun getCommercialUploadSuccess(code: Int, result: CommercialGet)
    fun getCommercialUploadFailure(code : Int, message : String)
}