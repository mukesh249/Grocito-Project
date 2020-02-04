package grocito.wingstud.groctiodriver.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import grocito.wingstud.groctiodriver.R
import grocito.wingstud.groctiodriver.activity.MainActivity
import grocito.wingstud.groctiodriver.databinding.ActivityLoginKtBinding
import grocito.wingstud.groctiodriver.extensions.setLinkSpan
import grocito.wingstud.groctiodriver.rest.responses.LoginResponse
import kotlinx.android.synthetic.main.activity_login_kt.*
import org.jetbrains.anko.toast

class LoginKt : AppCompatActivity() , Loginlistener {


    var viewModel : LoginVIewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginKtBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_kt)
        viewModel = ViewModelProviders.of(this).get(LoginVIewModel::class.java)
        binding.loginViewModel = viewModel
        viewModel!!.loginlistener = this

        val textWatcher = TextListener()
        username_et.addTextChangedListener(textWatcher)
        password_et.addTextChangedListener(textWatcher)

//        forgot_bt.setOnClickListener {  startActivity(Intent(this, ForgotPasswordActivity::class.java)) }

        toc.movementMethod = LinkMovementMethod.getInstance()
        toc.text = SpannableString("Accept the Terms and Condition").apply {
            setLinkSpan("Terms and Condition", {
                val intent = Intent(this@LoginKt,TermsAndCondition::class.java)
                startActivity(intent)
            }, ContextCompat.getColor(this@LoginKt, R.color.Grey_600)
                )
        }
        check_box.setOnClickListener { buttonEnable() }
    }


    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess(loginResponse: LoginResponse?) {
        if (loginResponse!!.status_code == 1) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onFailure(message: String) {
        toast(message)
    }

    private fun buttonEnable() {
        sign_bt.isEnabled = !username_et.text.isNullOrEmpty() && !password_et.text.isNullOrEmpty() && check_box.isChecked
    }

    inner class TextListener : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            buttonEnable()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    }
}
