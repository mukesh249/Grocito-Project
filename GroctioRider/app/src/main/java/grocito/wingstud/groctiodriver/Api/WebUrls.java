package grocito.wingstud.groctiodriver.Api;


/**
 * Created by Advosoft2 on 6/28/2017.
 */

public class WebUrls {
    public static final String BASE_URL = "https://www.grocito.com/";

    public final static String Login = "r-api-login";
    public final static String OtpApi= "r-check-otp";
    public final static String ResendOtpApi = "r-resend-otp";
    public final static String ProfileApi = "get-user";
    public final static String ComminsionWalletListApi = "get-commission-wallet";
    public final static String NotificationListApi = "get-notification";
    public final static String TodayPayment = "get-today-commission";

    //Google api for Tracking
    public static final String baseURL = "https://maps.googleapis.com";

    public static ApiConfig getGoogleAPI(){
        return RetrofitClient.getRetrofitClient(baseURL).create(ApiConfig.class);
    }

}
