package umc.mobile.project.mypage.profile

import Post
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.FragmentMyprofileBinding
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGet
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGetResult
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGetService
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetResult
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetService
import umc.mobile.project.ram.my_application_1.*


class MyProfileFragment : Fragment(), PostUploadGetResult {
    private lateinit var viewBinding: FragmentMyprofileBinding
    //private lateinit var orderRVAdapter: OrderRVAdapter
    //private lateinit var reviewRVAdapter: ReviewRVAdapter
    var postUploadList = ArrayList<Post>()

    var myProfileActivity: MyProfileActivity? = null

    var name : String = ""
    var image : String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myProfileActivity = context as MyProfileActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMyprofileBinding.inflate(layoutInflater)

        Log.d("name: ", arguments?.getString("name").toString())
        Log.d("image: ", arguments?.getString("image").toString())

        name = arguments?.getString("name").toString()
        image = arguments?.getString("image").toString()

        viewBinding.nickname.text = name

        Log.d("name: ", name)
        Log.d("image: ", image)

        if(image != "null"){
            Glide.with(this).load(arguments?.getString("image")).into(viewBinding.profileImage)
        }

        //initReview()

        // 프로필 사진 변경
        viewBinding.changeProfile.setOnClickListener {

        }

        // 변경된 사진있으면 사진 보내고 페이지 변경
        viewBinding.profileRevise.setOnClickListener {
            myProfileActivity!!.replaceFragment(1)
        }

        viewBinding.phoneNumRevise.setOnClickListener {
            myProfileActivity!!.replaceFragment(2)
        }

        // 뒤로 가기
        viewBinding.myprofileActionbar.appbarBackBtn.setOnClickListener {

            (context as MyProfileActivity).finish()
        }

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        getPostUpload()
        //getEmotionStatus()
    }

    private fun initRecycler(result : ArrayList<Post>) {
        val orderRVAdapter = OrderRVAdapter(result)
        viewBinding.orderList.adapter = orderRVAdapter
        viewBinding.orderList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        Log.d("initRecycler ======================== ", "====")

        orderRVAdapter.setItemClickListener(object :
            OrderRVAdapter.OnItemClickListener {

            override fun onItemClick(application: Post) {
                val intent = Intent(context, MyCommercialDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun getPostUpload() {
        val postUploadGetService = PostUploadGetService()
        postUploadGetService.setPostUploadGetResult(this)
        postUploadGetService.getPostUpload(user_id_logined) // 임의로 지정
        user_id_var = user_id_logined // 상세목록 볼 때 현재 로그인된 유저를 보여줄 수 있게 덮어씌워주기

    }

    override fun getPostUploadSuccess(
        code: Int,
        result: ArrayList<Post>

    ) {
        initRecycler(result)

        //Toast.makeText(context, "업로드 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Toast.makeText(context, "업로드 불러오기 실패", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}