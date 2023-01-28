package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.databinding.ActivitySignUpAlarmKeywordBinding
import umc.mobile.project.signup.Auth.ApiService
import umc.mobile.project.signup.Auth.KeywordRequest
import umc.mobile.project.signup.Auth.KeywordResponse

class SignUpAlarmKeywordActivity : AppCompatActivity() {
    val TAG: String = "로그"
    private lateinit var viewBinding: ActivitySignUpAlarmKeywordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var arrayList = arrayListOf<String>()

        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/post")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)



        viewBinding = ActivitySignUpAlarmKeywordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)



//        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}")



        viewBinding.btnInputAlarm.setOnClickListener {

            var keyWord: String = viewBinding.etInputAlarmKeyword.text.toString()

            arrayList.add(keyWord)
//            Log.d(TAG, "onCreate: ${keyWord},     ${arrayList}")
        }
        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpRegionActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("keyWord", viewBinding.etInputAlarmKeyword.text.toString())

            apiService.getUserKeywordId(KeywordRequest("1", "2", "3", "4", "5", "6"))
                .enqueue(object : Callback<KeywordResponse>{
                override fun onResponse(
                    call: Call<KeywordResponse>?,
                    response: Response<KeywordResponse>
                ) {
                    if (response.isSuccessful){
                        val keywordResponseData = response.body()
                        if(keywordResponseData != null){
                            when(keywordResponseData.code){
                                1000 -> Toast.makeText(this@SignUpAlarmKeywordActivity, "성공!.", Toast.LENGTH_SHORT).show()

                                else -> Toast.makeText(this@SignUpAlarmKeywordActivity, "알수없는 애러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{

                    }
                }

                override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                }



            })

//            startActivity(intent)
//            overridePendingTransition(0, 0)
        }

        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)

        }
    }


}