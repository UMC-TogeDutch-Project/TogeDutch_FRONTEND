package umc.mobile.project.commercial

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import umc.mobile.project.commercial.Auth.CommercialDetailGet.CommercialDetailGetResult
import umc.mobile.project.commercial.Auth.CommercialDetailGet.CommercialDetailGetService
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGet
import umc.mobile.project.commercial.Auth.RefundPost.*
import umc.mobile.project.databinding.ActivityCommercialDetailBinding
import umc.mobile.project.ram.Geocoder_location

var commercial_tid = ""
class CommercialDetailActivity:AppCompatActivity(), CommercialDetailGetResult, RefundResult {
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
        viewBinding.btnRefund.setOnClickListener {
            refund()
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
    private fun refund(){
        val refundService = RefundService()
        refundService.setRefundResult(this)
        refundService.sendPost(commercial_tid)
    }

    override fun getCommercialUploadFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun refundSuccess(
        amount: Amount?,
        approvedCancelAmount: Approved_cancel_amount,
        canceledAmount: Canceled_amount,
        cancelAvailableAmount: Cancel_available_amount
    ) {
        Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    override fun refundFailure() {

    }
}