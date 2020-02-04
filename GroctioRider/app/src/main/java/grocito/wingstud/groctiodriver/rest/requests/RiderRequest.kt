package grocito.wingstud.groctiodriver.rest.requests

import com.google.gson.annotations.SerializedName

class RiderRequest(
        @SerializedName("user_id")
        val user_id: String
)