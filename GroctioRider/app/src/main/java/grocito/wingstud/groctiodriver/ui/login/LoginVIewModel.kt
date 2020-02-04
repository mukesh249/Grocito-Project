package grocito.wingstud.groctiodriver.ui.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import grocito.wingstud.groctiodriver.App
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.extensions.defaultSharedPreferences
import grocito.wingstud.groctiodriver.extensions.putString
import grocito.wingstud.groctiodriver.rest.network.RestClient
import grocito.wingstud.groctiodriver.rest.requests.LoginRequest
import grocito.wingstud.groctiodriver.rest.responses.LoginResponse
import grocito.wingstud.groctiodriver.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginVIewModel : ViewModel() {
    val activity: Activity? = null
    val context: Context? = null
    var email: String? = null
    var password: String? = null

    var loginlistener: Loginlistener? = null

    fun loginButtonClick(view: View) {
        loginlistener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginlistener?.onFailure("Invalid User name or password ")
            return
        }

//        val loginResponse  = LoginRepository().userLogin(userName!!,password!!,UUID.randomUUID().toString())
        val loginRequest = LoginRequest(
                email!!,
                password!!,
                App.get().defaultSharedPreferences.getString("Token","")
        )
        RestClient.getInstance().apiInterface.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                dialog.dismiss()
                context?.toast("Network connection error.")
                Log.e(TAG, "onFailure", t);
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                dialog.dismiss()
                if (response.isSuccessful && response.body()!!.status_code == 1) {
                    val body = response.body()!!
                    App.get().defaultSharedPreferences.putString("ImagePath", body.img_path)
                    AccountManager.setUserAccount(body.data.toAccount(loginRequest.device_token!!))
                    Log.d(TAG, "LOGIN SUCCESS" + body)
                    loginlistener?.onSuccess(response.body())
                } else {
                    if (response.body() != null && response.body()!!.message != null)
                        context?.toast( response.body()!!.message!!)
                    else context?.toast( response.body()!!.message!!)
                }
            }
        })
    }
}