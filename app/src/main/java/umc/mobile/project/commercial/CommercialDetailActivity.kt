package umc.mobile.project.commercial

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import umc.mobile.project.commercial.Auth.CommercialDetailGet.CommercialDetailGetResult
import umc.mobile.project.commercial.Auth.CommercialDetailGet.CommercialDetailGetService
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGet
import umc.mobile.project.databinding.ActivityCommercialDetailBinding
import umc.mobile.project.ram.Geocoder_location

class CommercialDetailActivity:AppCompatActivity(), CommercialDetailGetResult {
    lateinit var viewBinding: ActivityCommercialDetailBinding
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCommercialDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewBinding.backBtn.setOnClickListener{
            finish()
        }

        getCommercialUpload()

    }


    private fun getCommercialUpload(){
        val commercialDetailGetService = CommercialDetailGetService()
        commercialDetailGetService.setCommercialDetailGetResult(this)
        commercialDetailGetService.getCommercialDetail(ad_id)
    }

    override fun getCommercialUploadSuccess(code: Int, result: CommercialGet) {
        viewBinding.comTvTitle.text = result.store
        viewBinding.comTvInform.text = result.information
        viewBinding.comTvMenu.text = result.mainMenu
        viewBinding.comTvTip.text = result.deliveryTips.toString() + " 원"
        val geocoderLocation = Geocoder_location()
        viewBinding.comTvPlace.text = geocoderLocation.calculate_location(this, result.latitude, result.longitude)
        viewBinding.comTvRequest.text = result.request

        Glide.with(this).load(result.image).into(viewBinding.imgViewImg)

    }

    override fun getCommercialUploadFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }
}