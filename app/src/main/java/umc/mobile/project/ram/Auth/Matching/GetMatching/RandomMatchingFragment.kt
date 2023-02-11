package umc.mobile.project.ram.Auth.Matching.GetMatching

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import umc.mobile.project.MemberData
import umc.mobile.project.databinding.FragmentRandomMatchingBinding
import umc.mobile.project.ram.Auth.Matching.GetMatchingAccept.MatchingAcceptGetResult
import umc.mobile.project.ram.Auth.Matching.GetMatchingAccept.MatchingAcceptGetService


class RandomMatchingFragment: Fragment(), MatchingGetResult, MatchingAcceptGetResult {
    private lateinit var viewBinding: FragmentRandomMatchingBinding

    var userIdx : Int = -1
    var name : String = ""
    var image : String = ""

    var getUserIdx : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentRandomMatchingBinding.inflate(layoutInflater)

        Log.d("user_id: ", arguments?.getInt("user_id").toString())
        Log.d("name: ", arguments?.getString("name").toString())
        Log.d("image: ", arguments?.getString("image").toString())

        userIdx = arguments?.getInt("user_id")!!
        name = arguments?.getString("name").toString()
        image = arguments?.getString("image").toString()

        Log.d("userIdx: ", userIdx.toString())
        Log.d("name: ", name)
        Log.d("image: ", image)

        getUserIdx = userIdx

        viewBinding.nickName.text = name
        if(image != "null"){
            Glide.with(this).load(arguments?.getString("image")).into(viewBinding.profileImage)
        }

        Toast.makeText(context, "랜덤 매칭 성공", Toast.LENGTH_SHORT).show()

        // 메이트 매칭 신청 (알람 가게 설정)
        viewBinding.btnMatchingApplication.setOnClickListener {
            getMatchingAccept()
            Toast.makeText(context, "메이트 매칭 신청 완료", Toast.LENGTH_SHORT).show()
        }

        // 재추천 받기
        viewBinding.btnRecommend.setOnClickListener {
           getMatching()
        }

        return viewBinding.root
    }

    // 랜덤 매칭
    fun getMatching(){
        val matchingGetService = MatchingGetService()
        matchingGetService.setMatchingGetResult(this)
        matchingGetService.getRandomMatching(38) // 임의로 지정 (post_id 넣으면 됨)
    }

    override fun getMatchingSuccess(code: Int, result: MemberData) {
        if(result.userIdx != 0 && result.name != null) {
            //bindingView.randomFramelayout.visibility = View.VISIBLE

            viewBinding.nickName.text = result.name
            name = result.name

            Glide.with(this).load(result.image).into(viewBinding.profileImage)


            Toast.makeText(context, "랜덤 매칭 성공", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "랜덤 매칭 3회 초과", Toast.LENGTH_SHORT).show()
        }
        getUserIdx = result.userIdx
        Log.d("success getUserIdx 값 : ", getUserIdx.toString())
    }

    override fun getMatchingFailure(code: Int, message: String) {
        Toast.makeText(context, "랜덤 매칭 실패", Toast.LENGTH_SHORT).show()
        getUserIdx = 0
        Log.d("fail getUserIdx 값 : ", getUserIdx.toString())
    }

    // 메이트 매칭 신청
    fun getMatchingAccept() {
        val matchingAcceptGetService = MatchingAcceptGetService()
        matchingAcceptGetService.setMatchingAcceptGetResult(this)
        matchingAcceptGetService.getMatchingAccept(getUserIdx, 39)    // 임의로 지정
    }

    override fun getMatchingAcceptSuccess(code: Int, result: Int) {
        Log.d("result 변환 값 ==========================", result.toString())

        Toast.makeText(context, "메이트 매칭 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getMatchingAcceptFailure(code: Int, message: String) {
        Toast.makeText(context, "메이트 매칭 실패", Toast.LENGTH_SHORT).show()
    }
}