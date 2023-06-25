package umc.mobile.project.mypage.profile

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.MyApplication
import umc.mobile.project.R
import umc.mobile.project.announcement.access_token
import umc.mobile.project.databinding.FragmentMypagePhonenumberReviseBinding
import umc.mobile.project.mypage.ChangePassword.PasswordPatchService
import umc.mobile.project.mypage.ChangePhoneNumber.ChangePhoneNumberResult
import umc.mobile.project.mypage.ChangePhoneNumber.PhoneNumberPatchRequest
import umc.mobile.project.mypage.ChangePhoneNumber.PhoneNumberPatchResult
import umc.mobile.project.mypage.ChangePhoneNumber.PhoneNumberPatchService
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.signup.SmsApiService
import umc.mobile.project.signup.SmsRequest
import umc.mobile.project.signup.SmsResponse

class MyPhoneNumReviseFragment : Fragment(), PhoneNumberPatchResult {
    private lateinit var viewBinding: FragmentMypagePhonenumberReviseBinding

    val TAG: String = "로그"
    var checkSum : String = ""
    var name : String = ""
    var image : String = ""
    var newPhoneNumber : String = ""

    var newPhoneNumberEditText : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMypagePhonenumberReviseBinding.inflate(layoutInflater)

        //Retrofit2 선언
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val smsApiService = retrofit.create(SmsApiService::class.java)

        initActionBar()

        Log.d("name: ", arguments?.getString("name").toString())

        name = arguments?.getString("name").toString()

        viewBinding.nickname.text = name

        Log.d("name: ", name)

        image = MyApplication.prefs.getString("image", "")

        if(image != "null"){
            Glide.with(this).load(MyApplication.prefs.getString("image", "")).into(viewBinding.profileImage)
        }

        newPhoneNumberEditText = viewBinding.newPhoneNumber

        if(viewBinding.newPhoneNumber.text.toString() != null) {
            viewBinding.phoneAuthentication.setBackgroundResource(R.drawable.sign_up_btn_background_blue_color)
        }

        viewBinding.phoneAuthentication.setOnClickListener {
            val to = viewBinding.newPhoneNumber.text.toString()
            Log.d(TAG, "onCreate: ${to}")

            smsApiService.sendCheckNum(SmsRequest(to)).enqueue(object : Callback<SmsResponse> {
                override fun onResponse(call: Call<SmsResponse>, response: Response<SmsResponse>) {
                    val smsResponseData = response.body()
                    if(smsResponseData?.result != null){
                        Log.d(TAG, "onResponse: 응답 성공 ${smsResponseData.result.smsConfirmNum}")
                        checkSum = smsResponseData.result.smsConfirmNum
                    }
                    else{
                        Log.d(TAG, "onResponse: 응답실패")
                    }
                }

                override fun onFailure(call: Call<SmsResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: 실패 ${t}")

                }


            })
        }

        viewBinding.authenticationNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // text가 변경된 후 호출
                // s에는 변경 후의 문자열이 담겨 있다.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // text가 변경되기 전 호출
                // s에는 변경 전 문자열이 담겨 있다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // text가 바뀔 때마다 호출된다.
                // 우린 이 함수를 사용한다.
                checkAgreeNum()
            }
        })

        // 전화번호 변경
        viewBinding.btnAuthentication.setOnClickListener {
            if(checkAgreeNum()) {
                save()
            }
        }

        return viewBinding.root
    }

    private fun initActionBar() {
        viewBinding.include.appbarPageNameLeftTv.text = "내 프로필 수정"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            (context as MyProfileActivity).supportFragmentManager.beginTransaction().remove(this).commit()
            (context as MyProfileActivity).supportFragmentManager
                .popBackStack()
        }
    }

    fun checkAgreeNum():Boolean{

        var checkPhoneNum = viewBinding.authenticationNum.text.toString()

        if (checkSum != checkPhoneNum){
            viewBinding.errorMessage.setText("인증번호가 올바르지 않습니다.")
            viewBinding.authenticationNum.setTextColor(Color.parseColor("#C854FF"))
            return false
        } else {
            viewBinding.errorMessage.setText("")
            viewBinding.authenticationNum.setTextColor(Color.parseColor("#FF000000"))
            return true
        }
    }

    // 서버에 보낼 데이터 담아주는 함수
    private fun getChangePhoneNumber() : PhoneNumberPatchRequest {
        newPhoneNumber = newPhoneNumberEditText?.text.toString()

        return PhoneNumberPatchRequest(newPhoneNumber)
    }

    private fun save() {
        val phoneNumberPatchService = PhoneNumberPatchService()
        phoneNumberPatchService.setPhoneNumberPatchResult(this)
        phoneNumberPatchService.changePhoneNumber(
            access_token, user_id_logined,
            getChangePhoneNumber())
    }

    override fun changePhoneNumberSuccess(result: ChangePhoneNumberResult) {
        Log.d("전화번호 변환 값 ==========================", result.phone)
        //Toast.makeText(requireContext(), "전화번호 변경 성공.", Toast.LENGTH_SHORT).show()
    }

    override fun changePhoneNumberFailure() {
        Toast.makeText(requireContext(), "전화번호 변경 실패.", Toast.LENGTH_SHORT).show()
    }
}