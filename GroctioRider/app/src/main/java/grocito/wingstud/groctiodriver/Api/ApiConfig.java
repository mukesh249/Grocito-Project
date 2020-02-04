package grocito.wingstud.groctiodriver.Api;


import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiConfig {

    @Multipart
    @POST("api")
    Call<JsonObject> PostContract(
//                                  @Part List<MultipartBody.Part> photos,
            @Part MultipartBody.Part image,
            @Part("request") RequestBody name
    );

//    @Multipart
//    @POST("api/Requestjobs/createJob")
//    Call<JsonObject> postFixProblem(@Header("ln") String authorization,
//                                    @Part List<MultipartBody.Part> photos,
//                                    @Part MultipartBody.Part image,
//                                    @Part("data") RequestBody name);
//

    @Multipart
    @POST("api")
    Call<JsonObject> AddPost(@Part("request") RequestBody name,
                             @Part ArrayList<MultipartBody.Part> photos

    );

}
