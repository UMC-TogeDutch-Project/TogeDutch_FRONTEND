package umc.mobile.project.commercial.Auth.RefundPost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.commercial.commercial_cid
import umc.mobile.project.getRetrofit

class RefundService {
    private var amount : Amount = Amount(total = 1, tax_free = 1, vat = 1, point = 1, discount = 1)
    private var approved_cancel_amount : Approved_cancel_amount = Approved_cancel_amount(total = 1, tax_free = 1, vat = 1, point = 1, discount = 1, green_deposit = 1)
    private var canceled_amount : Canceled_amount = Canceled_amount(total = 1, tax_free = 1, vat = 1, point = 1, discount = 1,green_deposit = 1)
    private var cancel_available_amount : Cancel_available_amount = Cancel_available_amount(total = 1, tax_free = 1, vat = 1, point = 1, discount = 1, green_deposit = 1)

    private lateinit var refundResult : RefundResult

    fun setRefundResult(refundResult: RefundResult){
        this.refundResult = refundResult
    }

    fun sendPost(tid : String){

        val authService = getRetrofit().create(RefundRetrofitInterfaces::class.java)
        authService.sendPost(tid).enqueue(object: Callback<RefundResponse> {
            override fun onResponse(call: Call<RefundResponse>, response: Response<RefundResponse>) {
                Log.d("RECORD/SUCCESS",response.toString())
                val resp: RefundResponse = response.body()!!

                amount = resp.amount!!
                approved_cancel_amount = resp.approved_cancel_amount!!
                canceled_amount = resp.canceled_amount!!
                cancel_available_amount = resp.cancel_available_amount!!
                when(resp.status){
                    "CANCEL_PAYMENT" ->{
                        refundResult.refundSuccess(
                            resp.amount,
                            resp.approved_cancel_amount,
                            resp.canceled_amount,
                            resp.cancel_available_amount
                        )
                    }
                    else -> refundResult.refundFailure(resp.code, resp.msg)
                }





            }

            override fun onFailure(call: Call<RefundResponse>, t: Throwable) {
                Log.d("RECORD/FAILURE",t.message.toString())
            }
        })
    }
}