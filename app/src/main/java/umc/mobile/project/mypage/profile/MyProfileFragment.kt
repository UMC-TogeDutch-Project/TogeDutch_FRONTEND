package umc.mobile.project.mypage.profile

import Post
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.mobile.project.MyApplication
import umc.mobile.project.R
import umc.mobile.project.announcement.access_token
import umc.mobile.project.databinding.FragmentMyprofileBinding
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGet
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGetResult
import umc.mobile.project.mypage.profile.emotionStatus.EmotionStatusGetService
import umc.mobile.project.mypage.profile.profileImage.ChangeProfileResult
import umc.mobile.project.mypage.profile.profileImage.profileImagePatchResult
import umc.mobile.project.mypage.profile.profileImage.profileImagePatchService
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetResult
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetService
import umc.mobile.project.ram.my_application_1.*
import java.io.File


class MyProfileFragment : Fragment(), PostUploadGetResult, EmotionStatusGetResult,
    profileImagePatchResult {
    private lateinit var viewBinding: FragmentMyprofileBinding
    //private lateinit var orderRVAdapter: OrderRVAdapter
    //private lateinit var reviewRVAdapter: ReviewRVAdapter
    var postUploadList = ArrayList<Post>()

    var myProfileActivity: MyProfileActivity? = null

    var name : String = ""
    var image : String = ""

    var score_list = ArrayList<EmotionStatusGet>()

    private lateinit var profileImage : ImageView
    private lateinit var body : MultipartBody.Part

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

        name = arguments?.getString("name").toString()

        viewBinding.nickname.text = name

        Log.d("name: ", name)

        image = MyApplication.prefs.getString("image", "")

        if(image != "null"){
            Glide.with(this).load(image).into(viewBinding.profileImage)
        }

        profileImage = viewBinding.profileImage

        //프로필 이미지 수정
        viewBinding.changeProfile.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    navigateGallery()
                }

                // 갤러리 접근 권한이 없는 경우 & 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )

            }

        }


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
        getEmotionStatus()
    }

    private fun initRecycler(result : ArrayList<Post>) {
        Log.d("score_list size: ", score_list.size.toString())
        val orderRVAdapter = OrderRVAdapter(result, score_list)
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

    override fun getPostUploadSuccess(code: Int, result: ArrayList<Post>) {
        initRecycler(result)
        //Toast.makeText(context, "업로드 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Toast.makeText(context, "업로드 불러오기 실패", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

     //점수
    fun getEmotionStatus() {
        val emotionStatusGetService = EmotionStatusGetService()
        emotionStatusGetService.setEmotionStatusGetResult(this)
        emotionStatusGetService.getEmotionStatus(user_id_logined)
    }

    override fun getEmotionStatusSuccess(code: Int, result: ArrayList<EmotionStatusGet>) {
        score_list = result
        getPostUpload()
    }

    override fun getEmotionStatusFailure(code: Int, message: String) {
        Log.d("실패 : ", code.toString())
        Log.d("실패 : ", message)
    }

    //프로필 이미지

    // 권한 요청 승인 이후 실행되는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navigateGallery()
                }
                else
                    Toast.makeText(context, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //
            }

        }
    }

    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"
        Log.d("navigateGallery=====", "====")
        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 예외처리
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            // 2000: 이미지 컨텐츠를 가져오는 액티비티를 수행한 후 실행되는 Activity 일 때만 수행하기 위해서
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    Log.d("selectedImageUri: ", selectedImageUri.toString())

                    val file = File(absolutelyPath(selectedImageUri, requireContext()))
                    val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                    body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    Log.d("testt",file.name)
                    Log.d("testt", body.toString())

                    save()

                    profileImage.setImageURI(selectedImageUri)

                    Log.d("profileImage: ", profileImage.toString())

                    MyApplication.prefs.setString("image", selectedImageUri.toString())
                    Log.d("onActivityResult=====", "====")
                } else {
                    Toast.makeText(context, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(context, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

//    private fun getProfileImage() : profileImagePatchRequest{
//        Log.d("getProfileImage:: ", body.toString())
//        return profileImagePatchRequest(body)
//    }

    private fun save() {
        val profileImagePatchService = profileImagePatchService()
        profileImagePatchService.setProfileImagePatchResult(this)
        Log.d("save:: ", body.toString())
        profileImagePatchService.changeProfile(access_token, user_id_logined, body)
    }

    override fun profileImageSuccess(code: Int, result: ChangeProfileResult) {
        Log.d("비밀번호 변환 값 ==========================", result.image)
    }

    override fun profileImageFailure(code: Int, message: String) {
        Log.d("실패 : ", code.toString())
        Log.d("실패 : ", message)
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(context)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

}