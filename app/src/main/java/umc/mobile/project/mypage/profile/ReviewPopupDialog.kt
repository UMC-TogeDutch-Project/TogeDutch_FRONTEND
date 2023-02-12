package umc.mobile.project.mypage.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.R
//import umc.mobile.project.databinding.ReviewCollectionDialogBinding

class ReviewPopupDialog(context : Context): DialogFragment() {
    private lateinit var reviewRVAdapter: ReviewRVAdapter
    private lateinit var recyclerView : RecyclerView

    var reviewList = ArrayList<ReviewData>()
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button

    val TAG: String = "로그"

    fun start(){

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.review_collection_dialog)
        dlg.setCanceledOnTouchOutside(true)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

        recyclerView = dlg.findViewById<RecyclerView>(R.id.reviewList)
        initReview()

        btn_close = dlg.findViewById(R.id.btnClose)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    private fun initReview() {
        reviewList.apply {
            add(ReviewData("친절하고 약속을 잘 지켜서 좋았어요!"))
            add(ReviewData("좋았습니당"))
            add(ReviewData("굿굿"))
            add(ReviewData("친절해요"))
        }

        Log.d(TAG, "adapter에 넣는다~~~~~~~~~")
        reviewRVAdapter = ReviewRVAdapter(reviewList)
        Log.d(TAG, "나왔다~~~~~~~~~")

        for(i in 0 .. reviewList.size - 1) {
            Log.d(TAG, "reviewList: ${reviewList[i].review}")
        }
        Log.d(TAG, "reviewList 내용")

        recyclerView.adapter = reviewRVAdapter
        Log.d(TAG, "화면 연결~~~~~~~~~")

        reviewRVAdapter.notifyDataSetChanged()
    }
}