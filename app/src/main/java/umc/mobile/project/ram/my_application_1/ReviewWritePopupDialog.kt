package umc.mobile.project.ram.my_application_1

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.internal.ContextUtils.getActivity
import umc.mobile.project.R
import umc.mobile.project.ram.Auth.Application.Join.JoinApplicationGetResult
import umc.mobile.project.ram.Auth.Application.Join.JoinApplicationGetService
import umc.mobile.project.ram.Auth.Application.ViewUpload.ApplicationGet
import umc.mobile.project.ram.Auth.Review.ReviewPostRequest
import umc.mobile.project.ram.Auth.Review.ReviewPostResult
import umc.mobile.project.ram.Auth.Review.ReviewPostService
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity

class ReviewWritePopupDialog(context : Context) : JoinApplicationGetResult, ReviewPostResult {
    val TAG: String = "로그"
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button
    private lateinit var btn_sendReview : Button

    private lateinit var badImage : ImageView
    private lateinit var goodImage : ImageView
    private lateinit var bestImage : ImageView

    private var isSelected_bad : Boolean = false
    private var isSelected_happy : Boolean = false
    private var isSelected_empty : Boolean = false

    private var emotionScore : Int = -1

    var reviewContent : String = ""

    var reviewText: EditText? = null

    var JoinApplicationList = ArrayList<ApplicationGet>()

    var context_ = context

    var applicationId : Int = -1

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.review_write_dialog)
        dlg.setCanceledOnTouchOutside(true)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

        btn_close = dlg.findViewById(R.id.close_btn)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        reviewText = dlg.findViewById<EditText>(R.id.reviewContent)

        // BAD
        badImage = dlg.findViewById<ImageView>(R.id.sad)
        badImage.setOnClickListener {
            
            isSelected_bad = !isSelected_bad
            badImage.isSelected = isSelected_bad

            if(badImage.isSelected){
                // 선택했을 때 처리
                emotionScore = 0
            }
            //////// 다른거 선택 취소!!!!!
            goodImage.isSelected = false
            bestImage.isSelected = false
            // 값 저장

        }


        // GOOD
        goodImage = dlg.findViewById<ImageView>(R.id.good)
        goodImage.setOnClickListener {

            isSelected_empty = !isSelected_empty
            goodImage.isSelected = isSelected_empty
            if(goodImage.isSelected){
                // 선택했을 때 처리
                emotionScore = 7
            }

            badImage.isSelected = false
            bestImage.isSelected = false
        }

        // BEST
        bestImage = dlg.findViewById<ImageView>(R.id.best)
        bestImage.setOnClickListener {

            isSelected_happy = !isSelected_happy
            bestImage.isSelected = isSelected_happy
            if(bestImage.isSelected){
                // 선택했을 때 처리
                emotionScore = 10
            }

            goodImage.isSelected = false
            badImage.isSelected = false

        }

        getJoinApplication()

        // 후기 보내기 클릭
        btn_sendReview = dlg.findViewById(R.id.btn_sendReview)
        btn_sendReview.setOnClickListener {
            Log.d(TAG, post_id_to_detail.toString())

            var index = 0
            for(i in 0..JoinApplicationList.size - 1){
                Log.d(TAG, "JoinApplicationList: ${JoinApplicationList[i].post_id}")
                if(post_id_to_detail == JoinApplicationList[i].post_id) {
                    applicationId = JoinApplicationList[i].application_id
                    break
                }
                index++
            }

            if(index < JoinApplicationList.size) {
                reviewText = dlg.findViewById<EditText>(R.id.reviewContent)

                save()
            } else {
                Log.d(TAG, "내가 참여한 공고 목록에 일치하는게 없음")
            }
        }

        dlg.show()
    }

    //GET
    private fun getJoinApplication() {
        val joinApplicationGetService = JoinApplicationGetService()
        joinApplicationGetService.setJoinApplicationGetResult(this)
        joinApplicationGetService.getJoinApplication(user_id_logined)
    }

    override fun getJoinApplicationSuccess(code: Int, result: ArrayList<ApplicationGet>) {
        JoinApplicationList.addAll(result)

        Log.d(TAG, post_id_to_detail.toString())

        for(i in 0..result.size - 1){
            Log.d(TAG, "ApplicationGetList: ${result[i].application_id}")
        }

        for(i in 0..JoinApplicationList.size - 1){
            Log.d(TAG, "JoinApplicationList: ${JoinApplicationList[i].application_id}")
            Log.d(TAG, "JoinApplicationList_PostID: ${JoinApplicationList[i].post_id}")
//            if(post_id_to_detail == JoinApplicationList[i].application_id) {
//                applicationId = JoinApplicationList[i].application_id
//
//            } else {
//                Log.d(TAG, "내가 참여한 공고 목록에 일치하는게 없음")
//            }
        }

        Log.d(TAG, "내가 참여한 공고 목록 불러오기 성공")
    }

    override fun getJoinApplicationFailure(code: Int, message: String) {
        Log.d(TAG, "내가 참여한 공고 목록 불러오기 실패")
    }

    // POST
    // 서버에 보낼 데이터 담아주는 함수
    private fun getPostReview() : ReviewPostRequest {
        reviewContent = reviewText?.text.toString()

        return ReviewPostRequest(emotionScore, reviewContent)
    }

    private fun save() {
        val reviewPostService = ReviewPostService()
        reviewPostService.setReviewResult(this)
        reviewPostService.sendReview(applicationId, getPostReview())
    }
    override fun reviewSuccess(result: Integer) {
        Log.d("result 변환 값 ==========================", result.toString())

        dlg.dismiss()

        Toast.makeText(context_, "후기 전송 성공.", Toast.LENGTH_SHORT).show()
    }

    override fun reviewFailure() {
        Toast.makeText(context_, "후기 전송 실패.", Toast.LENGTH_SHORT).show()
    }

}