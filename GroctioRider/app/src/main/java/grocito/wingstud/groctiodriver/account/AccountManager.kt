package grocito.wingstud.groctiodriver.account
import android.util.Log
import com.google.gson.Gson
import grocito.wingstud.groctiodriver.App
import grocito.wingstud.groctiodriver.extensions.defaultSharedPreferences
import grocito.wingstud.groctiodriver.extensions.putString

class AccountManager {

    companion object {
        private var self: UserAccount? = null

        fun clear(){
            self = null
        }

        fun setUserAccount(account: UserAccount) {
            val gson = Gson()
            val accountJson = gson.toJson(account)
            Log.d("SharedPref", "AccountJson ${accountJson}")

            App.get().defaultSharedPreferences.putString("account", accountJson)
            self = account
        }

        fun getUserAccount(): UserAccount? {
            if (self == null) {
                val accountString = App.get().defaultSharedPreferences.getString("account", null)
                if (accountString != null) {
                    val gson = Gson()
                    self = gson.fromJson<UserAccount>(accountString, UserAccount::class.java)
                }
            }
            return self
        }


    }
}