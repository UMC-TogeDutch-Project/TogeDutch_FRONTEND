package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import umc.mobile.project.commercial.CommercialListActivity
import umc.mobile.project.databinding.FragmentMypageBinding
import umc.mobile.project.login.LoginActivity
import umc.mobile.project.mypage.GetUser.UserGetResult
import umc.mobile.project.mypage.GetUser.UserGetService
import umc.mobile.project.notice.NoticeActivity
import umc.mobile.project.ram.my_application_1.MyPostActivity
import umc.mobile.project.profile.MyProfileActivity
import umc.mobile.project.ram.my_application_1.user_id_var
import umc.mobile.project.wishlist.WishListActivity
import umc.mobile.project.mypage.withdrawal.WithdrawalActivity

class MyPageFragment: Fragment(), UserGetResult {
    private lateinit var viewBinding: FragmentMypageBinding
    var userName : String = ""
    var userImage : String = ""
    val SUBACTIITY_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMypageBinding.inflate(layoutInflater)

        getUser()

        viewBinding.btnProfile.setOnClickListener {
            val intent = Intent(context, MyProfileActivity::class.java)
            intent.putExtra("name", userName)
            intent.putExtra("image", userImage)
            //startActivityForResult(intent, SUBACTIITY_REQUEST_CODE)
            startActivity(intent)
        }

        viewBinding.btnLogout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        viewBinding.participation.setOnClickListener {
            val intent = Intent(context, MyPostActivity::class.java)
            startActivity(intent)
        }

        viewBinding.favorite.setOnClickListener {
            val intent = Intent(context, WishListActivity::class.java)
            startActivity(intent)
        }

        viewBinding.advertise.setOnClickListener {
            val intent = Intent(context, CommercialListActivity::class.java)
            startActivity(intent)
        }

        viewBinding.notice.setOnClickListener {
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }

        viewBinding.alarmKeyword.setOnClickListener {

        }

        viewBinding.withdrawal.setOnClickListener {
            val intent = Intent(context, WithdrawalActivity::class.java)
            startActivity(intent)
        }

        return viewBinding.root
    }

    private fun getUser(){
        val userGetService = UserGetService()
        userGetService.setUserGetResult(this)
        userGetService.getUser(user_id_var) // 임의로 지정

    }

    override fun getUserSuccess(code: Int, result: MemberData) {
        viewBinding.tvName.text = result.name

        Log.d("name: ", result.name)
        Log.d("image: ", result.image.toString())

        if(result.image != null){
            Glide.with(this).load(result.image).into(viewBinding.imageView)
        }

        userName = result.name
        userImage = result.image.toString()

        Log.d("userName: ", userName)
        Log.d("userImage: ", userImage)
    }

    override fun getUserFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode== Activity.RESULT_OK){
//            Log.d("log: ", "log 찍힘")
//            if (data != null) {
//                userImage = data.getStringExtra().toString()
//                Glide.with(this).load(data.).into(viewBinding.imageView)
//            }
//        }
//    }

}