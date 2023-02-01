package umc.mobile.project.signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivitySignUpBinding
import umc.mobile.project.signup.Auth.ApiService
import umc.mobile.project.signup.Auth.SmsRequest
import umc.mobile.project.signup.Auth.SmsResponse
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpBinding

    val TAG: String = "로그"
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Retrofit2 선언
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)




        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpAlarmKeywordActivity::class.java)
            intent.putExtra("name", viewBinding.etInputName.text.toString())
            intent.putExtra("email", viewBinding.etInputEmail.text.toString())
            intent.putExtra("password", viewBinding.etInputPassword.text.toString())
            intent.putExtra("phoneNum", viewBinding.etInputPhoneNumber.text.toString())

            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        viewBinding.btnSendToCheckMsg.setOnClickListener {
            apiService.sendCheckNum(SmsRequest(viewBinding.etInputPhoneNumber.text.toString())).enqueue(object : Callback<SmsResponse>{
                override fun onResponse(
                    call: Call<SmsResponse>,
                    response: Response<SmsResponse>) {
                    Log.d(TAG, "onResponse:응답")
                    if(response.isSuccessful){
                        val smsResponseData = response.body()
                        if (smsResponseData != null){
                            when(smsResponseData.code){
                                1000 -> Toast.makeText(this@SignUpActivity, "성공! ${smsResponseData.result.requestId}", Toast.LENGTH_SHORT).show()
                            }

                        }
                        else{
                            Log.d(TAG, "onResponse: null임")
                        }
                    }
                    else{
                        Log.d(TAG, "onResponse:성공 실패")
                    }

                }

                override fun onFailure(call: Call<SmsResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: 요청 실패")
                }

            })
        }

        // addTextChangedListener의 경우 익명클래스이니 필수 함수들을 import 해줘야 함
        viewBinding.etInputEmail.addTextChangedListener(object : TextWatcher {
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
                checkEmail()
            }
        })

        viewBinding.etInputPasswordCheck.addTextChangedListener(object : TextWatcher {
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
                checkPassword()
            }
        })

        viewBinding.etInputCertificationNumber.addTextChangedListener(object : TextWatcher {
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



    }
    fun checkEmail():Boolean{
        var email = viewBinding.etInputEmail.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (p) {
            //이메일 형태가 정상일 경우
            viewBinding.tvEmailCheck.setText(" ")
            viewBinding.etInputEmail.setTextColor(Color.parseColor("#FF000000"))
            viewBinding.etInputEmail.setBackgroundResource(R.drawable.sign_up_edit_text_box)
            return true
        } else {
            viewBinding.tvEmailCheck.setText("이메일을 입력하세요.")
            viewBinding.etInputEmail.setTextColor(Color.parseColor("#C854FF"))
            viewBinding.etInputEmail.setBackgroundResource(R.drawable.sign_up_edit_text_box_purple)
            //또는 questionEmail.setTextColor(R.color.red.toInt())
            return false
        }
    }

    fun checkPassword():Boolean{
        var password = viewBinding.etInputPasswordCheck.text.toString().trim() //공백제거
        var passwordValidation = viewBinding.etInputPassword.text.toString().trim()

        if (password == passwordValidation) {
            //패스워드와 패스워드 체크가 같은 경우
            viewBinding.tvPasswordCheck.setText(" ")
            viewBinding.etInputPasswordCheck.setTextColor(Color.parseColor("#FF000000"))
            viewBinding.etInputPasswordCheck.setBackgroundResource(R.drawable.sign_up_edit_text_box)
            return true
        } else {
            viewBinding.tvPasswordCheck.setText("패스워드가 같지 않습니다.")
            viewBinding.etInputPasswordCheck.setTextColor(Color.parseColor("#C854FF"))
            viewBinding.etInputPasswordCheck.setBackgroundResource(R.drawable.sign_up_edit_text_box_purple)
            return false
        }
    }

    fun checkAgreeNum():Boolean{

        var checkPhoneNum = viewBinding.etInputCertificationNumber.text.toString()
        var checkSum : String = "16844"
        var password = viewBinding.etInputPasswordCheck.text.toString().trim() //공백제거
        var passwordValidation = viewBinding.etInputPassword.text.toString().trim()

        if (checkSum == checkPhoneNum && password == passwordValidation){
            if(passwordValidation != ""){
                viewBinding.btnNext.isEnabled = true
                viewBinding.btnNext.setBackgroundResource(R.drawable.sign_up_btn_background_blue_color)
                return true
            }
            else{
                viewBinding.btnNext.isEnabled = false
                viewBinding.btnNext.setBackgroundResource(R.drawable.sign_up_btn_background_grey_color)
                return false
            }
        }
        else{
            viewBinding.btnNext.isEnabled = false
            viewBinding.btnNext.setBackgroundResource(R.drawable.sign_up_btn_background_grey_color)
            return false
        }
    }



}