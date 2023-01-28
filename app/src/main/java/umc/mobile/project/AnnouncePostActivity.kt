package umc.mobile.project

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityAnnouncePostBinding


class AnnouncePostActivity : AppCompatActivity() {
    private var editText1: EditText? = null
    private var editText2: EditText? = null
    private var editText3: EditText? = null
    private var editText4: EditText? = null
    private var editText5: EditText? = null
    private var editText6: EditText? = null
    private var editText7: EditText? = null
    private var button: Button? = null

    private val viewBinding: ActivityAnnouncePostBinding by lazy {
        ActivityAnnouncePostBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.backBtn.setOnClickListener{
            finish()
        }

        editText1 = viewBinding.annEtTitle
        editText2 = viewBinding.annEtStore
        editText3 = viewBinding.annEtTip
        editText4 = viewBinding.annEtMinimum
        editText5 = viewBinding.annEtPlace
        editText6 = viewBinding.annEtTime
        editText7 = viewBinding.annEtPerson
        button = viewBinding.btnPost

        editText1!!.addTextChangedListener(textWatcher)
        editText2!!.addTextChangedListener(textWatcher)
        editText3!!.addTextChangedListener(textWatcher)
        editText4!!.addTextChangedListener(textWatcher)
        editText5!!.addTextChangedListener(textWatcher)
        editText6!!.addTextChangedListener(textWatcher)
        editText7!!.addTextChangedListener(textWatcher)



    }



    //editText 내용 입력시 버튼 활성화
    val textWatcher = object : TextWatcher {

        override
        fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2:Int) {

        }

        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override
        fun afterTextChanged(editable: Editable?) {

            val color = getColor(R.color.main_color)
            val color2 = getColor(R.color.grey_3)

            if (editText1?.text.toString().isNotEmpty() && editText2?.text.toString().isNotEmpty() && editText3?.text.toString().isNotEmpty()
                && editText4?.text.toString().isNotEmpty() && editText5?.text.toString().isNotEmpty() && editText6?.text.toString().isNotEmpty() && editText7?.text.toString().isNotEmpty()
                 ) {
                button?.isClickable =  true
                button?.backgroundTintList = ColorStateList.valueOf(color)
                Toast.makeText(applicationContext, "활성화", Toast.LENGTH_SHORT)
                    .show()
            } else  {
                button?.isClickable = false
                button?.backgroundTintList = ColorStateList.valueOf(color2)


            }
        }
    }


}