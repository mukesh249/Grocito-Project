package grocito.wingstud.groctiodriver.rest.repositories

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import grocito.wingstud.groctiodriver.account.AccountManager
import grocito.wingstud.groctiodriver.activity.MainActivity
import grocito.wingstud.groctiodriver.rest.network.RestClient
import grocito.wingstud.groctiodriver.rest.requests.LoginRequest
import grocito.wingstud.groctiodriver.rest.responses.LoginResponse
import grocito.wingstud.groctiodriver.util.Utils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    fun userLogin(email: String, password: String, deviceId: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()
        val loginRequest = LoginRequest(
                email,
                password,
                deviceId
        )
        RestClient.getInstance().apiInterface.login(loginRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        loginResponse.value = t.message
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful && response.body()!!.status_code == 1) {
                            val body = response.body()!!
//                            AccountManager.setUserAccount(body.toAccount(loginRequest.device_token!!))
                            Log.d(TAG, "LOGIN SUCCESS" + body)
                            loginResponse.value = response.body()?.toString()
                        } else {
                            if (response.body() != null && response.body()!!.message != null)
                                loginResponse.value = response.errorBody()?.toString()
                        }

//                        if (response.isSuccessful) {
//                            loginResponse.value = response.body()?.toString()
//                        } else {
//                            loginResponse.value = response.errorBody()?.toString()
//                        }
                    }
                })

        return loginResponse
    }
}