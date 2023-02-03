package umc.mobile.project.ram.my_application_1

import Post
import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityMypostBinding
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetResult
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetService
import umc.mobile.project.ram.chat.DeclarationPopupDialog
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

var postUploadList = ArrayList<Post>()

var user_id_var = 32
var user_id_logined = 32

var post_id_to_detail = 10

class MyPostActivity:AppCompatActivity(), PostUploadGetResult {
    lateinit var binding: ActivityMypostBinding
    lateinit var myPostRVAdapter: MyPostRVAdapter
    val applicationList = ArrayList<Post>()
    val joinList = ArrayList<Post>()
    lateinit var joinRVAdatpter: JoinRVAdatpter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initRecycler()


        // 스피너
        setupSpinnerText()
        setupSpinnerHandler()

    }

    private fun setupSpinnerText() {
        val txt = resources.getStringArray(R.array.spinner_txt)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_custom, txt)
        binding.spinnerBtn.adapter = adapter
    }



    private fun setupSpinnerHandler() {


        binding.spinnerBtn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0) { // 업로드 기준일 때
                    initActionBar()
                    binding.searchView.queryHint = "나의 공고를 검색해보세요"
                    binding.frameLayoutParticipate.visibility = View.INVISIBLE // 참여 화면 없애기
                    binding.frameLayoutDefault.visibility = View.VISIBLE // 업로드 화면 보이기
                }

//                else{ // 참여 일 때
//                    initRecycler_join()
//                    binding.mainActionbar.appbarPageNameLeftTv.text = "참여"
//                    binding.searchView.queryHint = "나의 참여내역을 검색해보세요"
//                    binding.frameLayoutParticipate.visibility = View.VISIBLE // 참여 화면 없애기
//                    binding.frameLayoutDefault.visibility = View.INVISIBLE // 업로드 화면 보이기
//                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initActionBar() {

//        binding.mainActionbar.appbarPageNameLeftTv.text = "나의 공고"
//
//        binding.mainActionbar.appbarBackBtn.setOnClickListener {
//            finish()
//        }

    }

    private fun initRecycler() {
        getPostUpload()

    }

//    private fun initRecycler_join(){
//
//        val curTime = Date().time
//        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//        var timestamp = Timestamp(java.util.Date().time)
//
//        var string = "2023-01-15T01:43:39.000+00:00"
//        joinList.apply {
//            add(
//                Post(7, "엽떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, timestamp,
//                    2, 1, "모집중", string, null, 2, 67.1234567, 127.3012345)
//            )
//            add(
//                Post(8, "베라 2인으로 같이 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 1000, 130000, timestamp,
//                    2, 1, "모집중", string, null, 2, 67.1234567, 127.3012345)
//            )
//
//
//            joinRVAdatpter = JoinRVAdatpter(joinList)
//            binding.rvParticipate.adapter = joinRVAdatpter
//
//            joinRVAdatpter.setItemClickListener(object:
//                JoinRVAdatpter.OnItemClickListener {
//                override fun onItemClick(application: Post) {
//                    val dlg = ReviewWritePopupDialog(this@MyPostActivity)
//                    dlg.start()
//                    fun open_activity(){
//                        val intent = Intent(this@MyPostActivity,CurrentApplicationActivity::class.java)
//                        startActivity(intent)
//                    }
//                }
//            })
//
//            joinRVAdatpter.notifyDataSetChanged()
//
////            initSearchView(joinRVAdatpter)
//
//        }
//    }

    private fun initSearchView(myPostRVAdapter: MyPostRVAdapter){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (binding.searchView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                // 검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    if(s != null){
                        if(s.isNotEmpty()){
                            myPostRVAdapter.filter.filter(s)
                        }
                    }
                    return false
                }
            })
        }
    }


    private fun getPostUpload(){
        val postUploadGetService = PostUploadGetService()
        postUploadGetService.setPostUploadGetResult(this)
        postUploadGetService.getPostUpload(user_id_logined) // 임의로 지정
        user_id_var = user_id_logined // 상세목록 볼 때 현재 로그인된 유저를 보여줄 수 있게 덮어씌워주기

    }

    override fun getPostUploadSuccess(
        code: Int,
        result: ArrayList<Post>

    ) {

        postUploadList.addAll(result)
        myPostRVAdapter = MyPostRVAdapter(postUploadList)
        binding.rvApplication.adapter = myPostRVAdapter

        myPostRVAdapter.setItemClickListener(object:
            MyPostRVAdapter.OnItemClickListener {

            override fun onItemClick(application: Post) {
                val intent = Intent(this@MyPostActivity, MyPostDetailActivity::class.java)
                startActivity(intent)


            }
        })

        myPostRVAdapter.notifyDataSetChanged()

        initSearchView(myPostRVAdapter)

        Toast.makeText(this, "업로드 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Toast.makeText(this, "업로드 불러오기 실패", Toast.LENGTH_SHORT).show()
    }
}