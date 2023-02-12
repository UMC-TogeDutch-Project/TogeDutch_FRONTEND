package umc.mobile.project.mypage.profile

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.R
import umc.mobile.project.mypage.profile.Review.ReviewGetResult
import umc.mobile.project.mypage.profile.Review.ReviewGetService

//import umc.mobile.project.databinding.ReviewCollectionDialogBinding

class ReviewPopupDialog(context : Context, post_id : Int): DialogFragment(), ReviewGetResult {
    private lateinit var reviewRVAdapter: ReviewRVAdapter
    private lateinit var recyclerView : RecyclerView

    var ccontext : Context = context

    var reviewList = ArrayList<ReviewGet>()
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button

    private lateinit var text : TextView

    val TAG: String = "로그"

    var post_id : Int = post_id

    override fun onResume() {
        super.onResume()

    }

    fun start(){


        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.review_collection_dialog)
        dlg.setCanceledOnTouchOutside(true)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

        recyclerView = dlg.findViewById<RecyclerView>(R.id.reviewList)

        text = dlg.findViewById<TextView>(R.id.nonText)
        text.visibility = View.GONE

        getReview()

        btn_close = dlg.findViewById(R.id.btnClose)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    private fun initReview(result : ArrayList<ReviewGet>) {
        val reviewRVAdapter = ReviewRVAdapter(result)
        recyclerView.adapter = reviewRVAdapter
        recyclerView.layoutManager = LinearLayoutManager(ccontext, LinearLayoutManager.VERTICAL, false)
    }

    // Get
    private fun getReview() {
        val getReviewService = ReviewGetService()
        getReviewService.setPostGetResult(this)
        getReviewService.getReview(15)   // 임의 값 지정 (post_id)
    }

    override fun getReviewSuccess(code: Int, result: ArrayList<ReviewGet>) {
        if (result.size != 0) {
            text.visibility = View.GONE
            initReview(result)

            for (i in 0..result.size - 1) {
                Log.d(TAG, "review id: ${result[i].reviewId}")
                Log.d(TAG, "review content: ${result[i].content}")
                Log.d(TAG, "post_id: ${result[i].postId}")
            }

           // Toast.makeText(ccontext, "리뷰 불러오기 성공", Toast.LENGTH_SHORT).show()
        } else {
            text.visibility = View.VISIBLE
        }
    }

    override fun getReviewFailure(code: Int, message: String) {
        Toast.makeText(ccontext, "리뷰 불러오기 실패", Toast.LENGTH_SHORT).show()

    }
}