package umc.mobile.project.mypage.alarmKeyword

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import umc.mobile.project.announcement.access_token
import umc.mobile.project.databinding.ActivityAlarmKeywordBinding
import umc.mobile.project.databinding.ActivityWishlistBinding
import umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword.AlarmKeywordGetResult
import umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword.AlarmKeywordGetService
import umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword.KeywordData
import umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword.AlarmKeywordPatchRequest
import umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword.AlarmKeywordPatchResult
import umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword.AlarmKeywordPatchService
import umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword.ChangeKeywordResult
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.signup.CustomAdapter
import umc.mobile.project.signup.DataVo

class MypageAlarmKeywordActivity : AppCompatActivity(), AlarmKeywordGetResult,
    AlarmKeywordPatchResult {
    val TAG: String = "로그"
    lateinit var viewBinding : ActivityAlarmKeywordBinding
    var keywordDataList : ArrayList<DataVo> = arrayListOf()
    //val keywordListIt : MutableList<String?> = mutableListOf(null, null, null, null, null, null)
    lateinit var customAdapter : CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAlarmKeywordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewBinding.appCompatSearch.setOnClickListener{
            val keyword = viewBinding.setMyKeyword.text.toString()
//            if(keywordList.size < 6){
            keywordDataList.apply { add(DataVo(keyword)) }
            Log.d(TAG, "onCreate:${keywordDataList.size}")
            customAdapter.notifyDataSetChanged()
            Log.d(TAG, "onCreate:${keywordDataList}")


            viewBinding.setMyKeyword.setText(null)
//            }
//            else{
//                Toast.makeText(this@SignUpAlarmKeywordActivity, "최대 키워드 갯수를 초과했습니다.", Toast.LENGTH_SHORT).show()
//            }

        }

        viewBinding.btnSave.setOnClickListener {
            save()
        }

    }

    override fun onResume() {
        super.onResume()
        initActionBar()
        getKeyword()
    }

    private fun initActionBar() {

        viewBinding.include.appbarPageNameLeftTv.text = "알람 키워드"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    private fun initRecyclerView(result: KeywordData) {
        Log.d("word1", result.word1.toString())
        Log.d("word2", result.word2.toString())
        Log.d("word3", result.word3.toString())
        Log.d("word4", result.word4.toString())
        Log.d("word5", result.word5.toString())
        Log.d("word6", result.word6.toString())
        if(result.word1.toString() != "null") {
            keywordDataList.apply { add(DataVo(result.word1.toString())) }
        }
        if(result.word2.toString() != "null") {
            keywordDataList.apply { add(DataVo(result.word2.toString())) }
        }
        if(result.word3.toString() != "null") {
            keywordDataList.apply { add(DataVo(result.word3.toString())) }
        }
        if(result.word4.toString() != "null") {
            keywordDataList.apply { add(DataVo(result.word4.toString())) }
        }
        if(result.word5.toString() != "null") {
            keywordDataList.apply { add(DataVo(result.word5.toString())) }
        }
        if(result.word6.toString() != "null") {
            keywordDataList.apply { add(DataVo(result.word6.toString())) }
        }
        customAdapter = CustomAdapter(keywordDataList)
        viewBinding.tableRecycleEdit.adapter = customAdapter
        viewBinding.tableRecycleEdit.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    private fun getKeyword() {
        val alarmKeywordGetService = AlarmKeywordGetService()
        alarmKeywordGetService.setKeywordGetResult(this)
        alarmKeywordGetService.getKeyword(user_id_logined)
    }

    override fun getKeywordSuccess(code: Int, result: KeywordData) {
        initRecyclerView(result)
//        Toast.makeText(this, "관심목록 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getKeywordFailure(code: Int, message: String) {
        Toast.makeText(this, "키워드 불러오기 실패", Toast.LENGTH_SHORT).show()
    }

    // 서버에 보낼 데이터 담아주는 함수
    private fun getChangeKeyword() : AlarmKeywordPatchRequest {
        val keywordListIt : MutableList<String?> = mutableListOf(null, null, null, null, null, null)

        var indx : Int = 0
        for(i in 0..keywordDataList.size - 1){

            keywordListIt[i] = keywordDataList[i].toString().substring(22, keywordDataList[i].toString().lastIndex)
            Log.d(TAG, "keywordListIt: ${indx}  ${keywordListIt[i]}")
            indx++
        }

        Log.d(TAG, "onResponse: ${keywordListIt[0]}, ${keywordListIt[1]}, ${keywordListIt[2]}, ${keywordListIt[3]}, ${keywordListIt[4]}, ${keywordListIt[5]}")

        return AlarmKeywordPatchRequest("${keywordListIt[0]}", "${keywordListIt[1]}", "${keywordListIt[2]}", "${keywordListIt[3]}", "${keywordListIt[4]}", "${keywordListIt[5]}")
    }

    private fun save() {
        val alarmKeywordPatchService = AlarmKeywordPatchService()
        alarmKeywordPatchService.setKeywordPatchResult(this)
        alarmKeywordPatchService.changeKeyword(access_token, user_id_logined, getChangeKeyword())
    }

    override fun changeKeywordSuccess(result: ChangeKeywordResult) {
        result.word1?.let { Log.d("키워드 변환 값1 ==========================", it) }
        result.word2?.let { Log.d("키워드 변환 값2 ==========================", it) }
        result.word3?.let { Log.d("키워드 변환 값3 ==========================", it) }
        result.word4?.let { Log.d("키워드 변환 값4 ==========================", it) }
        result.word5?.let { Log.d("키워드 변환 값5 ==========================", it) }
        result.word6?.let { Log.d("키워드 변환 값6 ==========================", it) }

        //Toast.makeText(this, "키워드 변경 성공.", Toast.LENGTH_SHORT).show()
    }

    override fun changeKeywordFailure() {
        Toast.makeText(this, "키워드 변경 실패.", Toast.LENGTH_SHORT).show()
    }
}