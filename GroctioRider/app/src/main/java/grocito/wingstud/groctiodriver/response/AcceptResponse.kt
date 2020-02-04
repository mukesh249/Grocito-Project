package grocito.wingstud.groctiodriver.response

import com.google.gson.annotations.SerializedName

class AcceptResponse (
        val status_code : Int,
        val message : String?,
        val error_message : String?
)