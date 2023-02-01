package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.databinding.ActivitySignUpUserPersonalInfoBinding
import umc.mobile.project.login.LoginActivity
import umc.mobile.project.signup.Auth.ApiService

class SignUpUserPersonalInfoActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpUserPersonalInfoBinding
    val TAG: String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding =ActivitySignUpUserPersonalInfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")
//        var keyWordIdx: Int = intent.getIntExtra("keyWordIdx", 1)
        var keyWordIdx: Int = 1
        var role = intent.getStringExtra("role")
        var status= intent.getStringExtra("status")
        var latitude = intent.getDoubleExtra("latitude", 50.02)
        var longitude = intent.getDoubleExtra("longitude", 60.02)

        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}, ${keyWordIdx}, ${status}, ${role}, ${latitude}, ${longitude}")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        viewBinding.btnNext.setOnClickListener {

          apiService.createNewUser(SignUpRequest(
              keyWordIdx, "${name}", "${role}", "${email}","${password}","${phoneNum}","${status}", latitude, longitude))
              .enqueue(object : Callback<SignUpResponse>{
                  override fun onResponse(
                      call: Call<SignUpResponse>,
                      response: Response<SignUpResponse>
                  ) {
                      Log.d(TAG, "onResponse: 요청성공 ${response.code()}, response.body: ${response.body()}, response.isSuccessful: ${response.isSuccessful}")
                      if(response.isSuccessful){
                          Log.d(TAG, "onResponse: ")
                          val signUpResponseData = response.body()
                          if(signUpResponseData != null){
                              when(signUpResponseData.code){
                                  1000 -> Log.d(TAG, "onResponse:응답 성공 userIdx: ${signUpResponseData.result?.userIdx}")
                              }
                          }
                          else{
                              Log.d(TAG, "onResponse:응답값이 null")
                          }
                      }
                      else{
                          Log.d(TAG, "onResponse:응답실패")
                      }
                  }

                  override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                      Log.d(TAG, "onFailure:요청 실패")
                  }

              })

            var intent = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)

        }
        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

    }
}