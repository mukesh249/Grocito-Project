package grocito.wingstud.groctiodriver.response

import com.google.gson.annotations.SerializedName

class SessionResponse (

        val status : String,
        val message : String?,
        val error_message : String?,
        @SerializedName("app_status")
        val appStatus : String?
)