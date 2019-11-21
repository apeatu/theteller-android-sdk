package gh.com.payswitch.thetellerandroid.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/v1.1/transaction/process")
    Call<String> chargeCard(@Body gh.com.payswitch.thetellerandroid.card.ChargeRequestBody body);

    @Headers("Content-Type: application/json")
    @POST("/v1.1/transaction/process")
    Call<String> chargeMomo(@Body gh.com.payswitch.thetellerandroid.ghmobilemoney.ChargeRequestBody body);
}