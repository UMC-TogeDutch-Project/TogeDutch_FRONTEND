package umc.mobile.project.announcement

import Post
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.*
import umc.mobile.project.HomeFragment.Companion.num1
import umc.mobile.project.announcement.Auth.PostImminentGet.PostImminentGetResult
import umc.mobile.project.announcement.Auth.PostImminentGet.PostImminentGetService
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetResult
import umc.mobile.project.announcement.Auth.PostRecentGet.PostRecentGetService
import umc.mobile.project.databinding.ActivityAnnounceListBinding
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.user_id_var
import umc.mobile.project.wishlist.GetLikePost.LikePostGetResult
import umc.mobile.project.wishlist.GetLikePost.LikePostGetService
import kotlin.collections.ArrayList

class AnnounceListActivity : AppCompatActivity(), PostRecentGetResult, PostImminentGetResult,
    LikePostGetResult {
    private lateinit var binding: ActivityAnnounceListBinding
    private lateinit var dataRecentRVAdapter: DataRecentRVAdapter
    private lateinit var dataImminentRVAdapter: DataImminentRVAdapter
//    var recentAnnounceData = ArrayList<Post>()
//    var imminentAnnounceData = ArrayList<Post>()
    private var postList = ArrayList<Post>()
    private var postList1 = ArrayList<Post>()

    var like_list = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnounceListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initActionBar()

        setupSpinnerText()
        setupSpinnerHandler()

//        if(num1 == 0){
//            initRecyclerViewRecent()
//
//        }
//
//        if(num1 == 1){
//            binding.spinner.setSelection(1)
//            initRecyclerViewImminent()
//            binding.recent.visibility = View.INVISIBLE // 최신순 화면 invisible
//            binding.imminent.visibility = View.VISIBLE
//
//        }

    }

    override fun onResume() {
        super.onResume()
        initActionBar()
        getLikePost()
//        if(num1 == 0)
//            initRecyclerViewRecent()
//        else
//            initRecyclerViewImminent()
        if(num1 == 0){
            initRecyclerViewRecent()

        }

        if(num1 == 1){
            binding.spinner.setSelection(1)
            initRecyclerViewImminent()
            binding.recent.visibility = View.INVISIBLE // 최신순 화면 invisible
            binding.imminent.visibility = View.VISIBLE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setupSpinnerText() {
        val txt = resources.getStringArray(R.array.spinner_txt2)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_custom, txt)
        binding.spinner.adapter = adapter
    }
    private fun setupSpinnerHandler() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if( num1 == 0 && position == 0) { // 최신순 일 때

                        binding.recent.visibility = View.VISIBLE // 최신순 화면 invisible
                        binding.imminent.visibility = View.INVISIBLE


                }
                else if (num1 == 0 && position == 1){
                    initRecyclerViewImminent()
                    binding.recent.visibility = View.INVISIBLE // 최신순 화면 invisible
                    binding.imminent.visibility = View.VISIBLE
                }

                else if(num1 == 1 && position == 0){
                    binding.recent.visibility = View.VISIBLE // 최신순 화면 invisible
                    binding.imminent.visibility = View.INVISIBLE // 마감임박 화면 visible

                }
                else if(num1 == 1 && position == 1){
                    initRecyclerViewRecent()
                    binding.recent.visibility = View.INVISIBLE // 최신순 화면 invisible
                    binding.imminent.visibility = View.VISIBLE // 마감임박 화면 visible

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    private fun initRecyclerViewRecent(){
        getPostLatest()

    }
    private fun initRecyclerViewImminent(){
        getPostImminent()

    }


    private fun initActionBar() {


        binding.annActionBar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }
//    private fun initSelected(){
//        val data = intent.getIntExtra("rereturnValue",0)
//        if (data == 0)
//        {
//            binding.recent.visibility = View.VISIBLE // 최신순 화면 visible
//            binding.imminent.visibility = View.INVISIBLE // 마감임박 화면 invisible
//
//        }
//        else{
//            binding.recent.visibility = View.INVISIBLE // 최신순 화면 invisible
//            binding.imminent.visibility = View.VISIBLE // 마감임박 화면 visible
//
//        }
//    }


//    fun sortCreated():Comparator<HomeData.Item> = object :Comparator<HomeData.Item>{
//        override fun compare(o1: HomeData.Item?, o2: HomeData.Item?): Int {
//
//            return o1!!.created_at!!.compareTo(o2!!.created_at!!)
//        }
//
//    }
private fun getPostLatest(){
    val postRecentGetService = PostRecentGetService()
    postRecentGetService.setPostGetResult(this)
    postRecentGetService.getPost( )

}
    private fun getPostImminent(){
        val postImminentGetService = PostImminentGetService()
        postImminentGetService.setPostGetResult(this)
        postImminentGetService.getPost( )

    }
    override fun recordSuccess(result: ArrayList<Post>) {
//        Toast.makeText(this, "공고 등록 성공.", Toast.LENGTH_SHORT).show()
//        finish()
        postList.addAll(result)
        dataRecentRVAdapter = DataRecentRVAdapter(postList, like_list)
        binding.rvMainRecent.adapter = dataRecentRVAdapter //리사이클러뷰에 어댑터 연결
        binding.rvMainRecent.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결



        dataRecentRVAdapter.setItemClickListener(object: DataRecentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: Post) {
                val intent = Intent(this@AnnounceListActivity, AnnounceDetailActivity::class.java)
                startActivity(intent)
            }
        })



        dataRecentRVAdapter.notifyDataSetChanged()
    }
    override fun recordFailure() {
        TODO("Not yet implemented")
    }

    override fun recordSuccess1(result: ArrayList<Post>) {
        postList1.addAll(result)
        dataImminentRVAdapter = DataImminentRVAdapter(postList1)
        binding.rvMainImminent.adapter = dataImminentRVAdapter
        binding.rvMainImminent.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결


        dataImminentRVAdapter.setItemClickListener(object: DataImminentRVAdapter.OnItemClickListener{
            override fun onItemClick(announceData: Post) {
                val intent = Intent(this@AnnounceListActivity, AnnounceListActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun recordFailure1() {
        TODO("Not yet implemented")
    }

    private fun getLikePost() {
        val likePostGetService = LikePostGetService()
        likePostGetService.setLikePostGetResult(this)
        likePostGetService.getLikePost(user_id_logined)
        user_id_var = user_id_logined // 상세목록 볼 때 현재 로그인된 유저를 보여줄 수 있게 덮어씌워주기

    }

    override fun getPostUploadSuccess(code: Int, result: ArrayList<Post>) {
        like_list = result
        //Toast.makeText(this, "관심목록 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Toast.makeText(this, "관심목록 불러오기 실패", Toast.LENGTH_SHORT).show()
    }
}
