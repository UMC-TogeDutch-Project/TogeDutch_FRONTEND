package umc.mobile.project.profile

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import umc.mobile.project.R
import umc.mobile.project.announcement.access_token
import umc.mobile.project.databinding.FragmentMypageProfileReviseBinding
import umc.mobile.project.mypage.ChangePassword.*
import umc.mobile.project.ram.my_application_1.user_id_logined


class MyProfileReviseFragment : Fragment(), PasswordPatchResult {
    private lateinit var viewBinding: FragmentMypageProfileReviseBinding

    var name : String = ""
    var image : String = ""
    var newPassword : String = ""

    var newPasswordEditText : EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMypageProfileReviseBinding.inflate(layoutInflater)

        newPasswordEditText = viewBinding.newPassword

        initActionBar()

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


        viewBinding.btnAuthentication.setOnClickListener {
            save()
        }

        viewBinding.authenticationPassword.addTextChangedListener(object : TextWatcher {
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

        return viewBinding.root
    }

    fun checkPassword():Boolean{
        var password = viewBinding.authenticationPassword.text.toString().trim() //공백제거
        var passwordValidation = viewBinding.newPassword.text.toString().trim()

        if (password == passwordValidation) {
            //패스워드와 패스워드 체크가 같은 경우
            viewBinding.errorMessage.setText(" ")
            viewBinding.authenticationPassword.setTextColor(Color.parseColor("#FF000000"))
            return true
        } else {
            viewBinding.errorMessage.setText("패스워드가 같지 않습니다.")
            viewBinding.authenticationPassword.setTextColor(Color.parseColor("#C854FF"))
            return false
        }
    }

    // 서버에 보낼 데이터 담아주는 함수
    private fun getChangePassword() : PasswordPatchRequest{
        newPassword = newPasswordEditText?.text.toString()

        return PasswordPatchRequest(newPassword)
    }

    private fun save() {
        val passwordPatchService = PasswordPatchService()
        passwordPatchService.setPasswordPatchResult(this)
        passwordPatchService.changePassword(
            access_token, user_id_logined,
            getChangePassword())
    }

    private fun initActionBar() {

        viewBinding.include.appbarPageNameLeftTv.text = "내 프로필 수정"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            (context as MyProfileActivity).supportFragmentManager.beginTransaction().remove(this).commit()
            (context as MyProfileActivity).supportFragmentManager
                .popBackStack()
        }
    }

    override fun changePasswordSuccess(result: ChangePasswordResult) {
        Log.d("비밀번호 변환 값 ==========================", result.password)
        Toast.makeText(requireContext(), "비밀번호 변경 성공.", Toast.LENGTH_SHORT).show()
    }

    override fun changePasswordFailure() {
        Toast.makeText(requireContext(), "비밀번호 변경 실패.", Toast.LENGTH_SHORT).show()
    }
}