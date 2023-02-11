package umc.mobile.project.ram.my_application_1

import umc.mobile.project.MemberData
import Post
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.FragmentRandomMatchingBinding
import umc.mobile.project.databinding.ItemMyPostBinding
import umc.mobile.project.ram.Auth.Matching.GetMatching.MatchingGetResult
import umc.mobile.project.ram.Auth.Matching.GetMatching.MatchingGetService
import umc.mobile.project.ram.Auth.Post.DeletePost.DeletePostResult
import umc.mobile.project.ram.Auth.Post.DeletePost.DeletePostService


import umc.mobile.project.ram.Geocoder_location
import java.util.*
import kotlin.collections.ArrayList

import kotlinx.coroutines.*

var isOk = false
class MyPostRVAdapter (
    private val applicationList: ArrayList<Post>
) :
    RecyclerView.Adapter<MyPostRVAdapter.ViewHolder>(), Filterable, MatchingGetResult {

    lateinit var context : Context

    var delete_position : Int = 0
    lateinit var viewBinding : FragmentRandomMatchingBinding

    lateinit var bindingView : ItemMyPostBinding

    var getUserIdx : Int = 0

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMyPostBinding = ItemMyPostBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context

        viewBinding = FragmentRandomMatchingBinding.inflate(LayoutInflater.from(viewGroup.context),
            viewGroup, false)

        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = applicationList.size


    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(applicationList[position])
        holder.itemView.setOnClickListener {
            post_id_to_detail = applicationList[position].post_id
            itemClickListener.onItemClick(applicationList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemMyPostBinding) : RecyclerView.ViewHolder(binding.root) , DeletePostResult {
        fun bind(post: Post) {
            // Log.d("현재 유저 아이디 =============", post.user_id.toString())

            var selected_random_btn : Int = 0
            var isSelected = false

            val txt_title : String = post.title

            var latLong_to_address : String = Geocoder_location().calculate_location(context, post.latitude, post.longitude)
            var txt_location = latLong_to_address

            val txt_time = post.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"

            val txt_recruited : Int = post.recruited_num
            val txt_recruits : Int = post.num_of_recruits

            Glide.with(context).load(post.image).centerCrop().into(binding.listItemPicture)

            binding.orderListTitle.text = txt_title // 제목
            binding.orderListLocation.text = txt_location// 위치
            binding.orderListTime.text = txt_time_substring
            binding.numRecruited.text = txt_recruited.toString() // 현재 사람
            binding.numRecruits.text = txt_recruits.toString() // 필요 인원

            // 수정 버튼
            binding.modifyBtn.setOnClickListener {
                val intent = Intent(context, PostRetouchActivity::class.java)
                intent.putExtra("post_id", post.post_id)
                context.startActivity(intent)
            }

            // 삭제 버튼
            binding.deleteBtn.setOnClickListener {
                delete_position = absoluteAdapterPosition
                val deletePostService = DeletePostService()
                deletePostService.setDeletePostResult(this)
                deletePostService.deletePost(post.post_id)

            }

            var timestampToSdf = Timestamp_to_SDF()
            //            2022-01-23T03:34:56.000+00:00
            val currentTime = timestampToSdf.timestamp_to_String(System.currentTimeMillis())
            println("현재 시간 : " + currentTime)
            if(post.order_time > currentTime && (post.recruited_num < post.num_of_recruits)){
                binding.btnRandom.visibility = View.INVISIBLE
            }

            //랜덤 버튼
            binding.btnRandom.setOnClickListener {
                isSelected = !isSelected
                if(isSelected) {
                    selected_random_btn++

                    bindingView = binding

                    // 첫 랜덤 매칭  -> 위랑 순서 바꿔야 매칭되었을때 화면 나옴
                    runBlocking {
                        launch {
                            getMatching()
                        }.join()


//                        launch {
//                            Log.d("getUserIdx 값 : ", getUserIdx.toString())
//
//                            if (getUserIdx != 0) {
//                                binding.randomFramelayout.visibility = View.VISIBLE
//
//                                // 메이트 매칭 신청 (알람 가게 설정)
//                                viewBinding.btnMatchingApplication.setOnClickListener {
//
//                                }
//
//                                // 재추천 받기
//                                viewBinding.btnRecommend.setOnClickListener {
//                                    getMatching()
//                                }
//
//                            }
//                        }
                    }
                }
                else {
                    selected_random_btn--
                    binding.randomFramelayout.visibility = View.GONE
                }
            }
        }

        override fun deletePostSuccess(result : Int) {
            println(result)
            removePost(delete_position)
            Log.d("공고 삭제 성공", "")
        }

        override fun deletePostFailure() {
            Log.d("공고 삭제 실패", "")
        }
    }

    fun removePost(position: Int){
        if(position > 0){
            applicationList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener



    // 검색
    val mDataListAll = ArrayList<Post>(applicationList)
    var mAccounts:MutableList<Post> = applicationList as MutableList<Post>
    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        // Automatic on background thread
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Post> = java.util.ArrayList<Post>()
            if(constraint!!.isEmpty()){
                filteredList.addAll(mDataListAll)
            }
            else{
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim{ it <= ' '}
                for(item in mDataListAll){
                    //filter 대상 setting
                    if(item.title.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }

                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        // Automatic on UI thread
        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mAccounts.clear()
            mAccounts.addAll(results?.values as Collection<Post>)
            notifyDataSetChanged()
        }

    }

    // 랜덤 매칭
    private fun getMatching(){
        val matchingGetService = MatchingGetService()
        matchingGetService.setMatchingGetResult(this)
        matchingGetService.getRandomMatching(32) // 임의로 지정
    }

    override fun getMatchingSuccess(code: Int, result: MemberData) {
        if(result.userIdx != 0 && result.name != null) {
            bindingView.randomFramelayout.visibility = View.VISIBLE

            viewBinding.nickName.text = result.name
            Glide.with(context).load(result.image).into(viewBinding.profileImage)


            Toast.makeText(context, "랜덤 매칭 성공", Toast.LENGTH_SHORT).show()

            // 메이트 매칭 신청 (알람 가게 설정)
            viewBinding.btnMatchingApplication.setOnClickListener {
                Toast.makeText(context, "메이트 매칭 신청", Toast.LENGTH_SHORT).show()
            }

            // 재추천 받기
            viewBinding.btnRecommend.setOnClickListener {
                getMatching()
            }
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

}