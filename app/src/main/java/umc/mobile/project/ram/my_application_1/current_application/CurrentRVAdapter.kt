package umc.mobile.project.ram.my_application_1.current_application

import android.content.Context
import Post
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.databinding.ItemApplyCurrentBinding

import umc.mobile.project.ram.Auth.Application.Accept.PostAcceptResult
import umc.mobile.project.ram.Auth.Application.Accept.PostAcceptService
import umc.mobile.project.ram.Auth.Application.Deny.PostDenyResult
import umc.mobile.project.ram.Auth.Application.Deny.PostDenyService
import umc.mobile.project.ram.Auth.Application.Deny.Result
import umc.mobile.project.ram.Auth.Application.GetUser.UserGet
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetResult
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetService
import umc.mobile.project.ram.Auth.Application.ViewUpload.ApplicationGet
import umc.mobile.project.ram.Auth.Application.ViewUpload.ViewUploadGetService
import umc.mobile.project.ram.Auth.Post.GetPost.PostGetResult
import umc.mobile.project.ram.Auth.Post.GetPost.PostGetService
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.my_application_1.Timestamp_to_SDF
import java.sql.Timestamp

class CurrentRVAdapter(private val currentList: ArrayList<ApplicationGet>) : RecyclerView.Adapter<CurrentRVAdapter.ViewHolder>(){

    lateinit var context : Context
    var application_id = 0

    // 아이템 레이아웃 결합
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemApplyCurrentBinding = ItemApplyCurrentBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context
        return ViewHolder(binding)
    }

    // 아이템 개수
    override fun getItemCount(): Int = currentList.size

    // view에 내용 입력
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentList[position])
            notifyItemChanged(position)
        }
    }

    // 레이아웃 내 view 연결
    inner class ViewHolder(val binding: ItemApplyCurrentBinding) : RecyclerView.ViewHolder(binding.root), UserGetResult,PostDenyResult, PostAcceptResult,
        PostGetResult {
        fun bind(currentApplicatoin: ApplicationGet) {

            val userGetService = UserGetService()
            userGetService.setUserGetResult(this)
            userGetService.getUser(currentApplicatoin.user_id)

            val postGetService = PostGetService()
            postGetService.setPostGetResult(this)
            postGetService.getPost(currentApplicatoin.post_id)


            binding.itemAcceptBtn.setOnClickListener {
                val postAcceptService = PostAcceptService()
                postAcceptService.setAcceptResult(this)
                postAcceptService.sendAccept(currentApplicatoin.application_id)
            }

            binding.itemRefuseBtn.setOnClickListener {
                val postDenyService = PostDenyService()
                postDenyService.setDenyResult(this)
                postDenyService.sendDeny(currentApplicatoin.application_id)
            }
        }

        override fun getUserSuccess(code: Int, result: UserGet) {
            val txtUserID : String = result.name // 신청 유저 이름 ㅏㄱ져오기
            binding.itemAlarmIdTxt.text = txtUserID
        }

        override fun getUserFailure(code: Int, message: String) {
            TODO("Not yet implemented")
        }

        override fun getPostSuccess(code: Int, result: Post) {
            Log.d("============================================= 성공 !!!!!! ===================", "result.title")
            val txtSubject : String = result.title // 제목 가져오기
            binding.itemSubjectTxt.text = txtSubject

            Glide.with(context).load(result.image).centerCrop().into(binding.itemShopImg) // 이미지 가져오기

            val timestampToSdf = Timestamp_to_SDF()
            var txtDate : String = timestampToSdf.convert(result.created_at) // 시간 가져오기
            binding.itemDateTxt.text = txtDate
        }

        override fun getPostFailure(code: Int, message: String) {
            TODO("Not yet implemented")
        }


        override fun DenySuccess(result: Result) {
            binding.itemAlarmTxt.text = "님의 요청이 거절되었습니다."
        }

        override fun DenyFailure() {
            TODO("Not yet implemented")
        }

        override fun AcceptSuccess(result: umc.mobile.project.ram.Auth.Application.Accept.Result) {
            binding.itemAlarmTxt.text = "님의 요청이 수락되었습니다."
        }

        override fun AcceptFailure() {
            TODO("Not yet implemented")
        }
    }

    interface OnItemClickListener {
        fun onItemClick(currentApplicatoin: ApplicationGet)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener




}