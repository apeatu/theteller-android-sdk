package gh.com.payswitch.thetellerandroid.data;

import gh.com.payswitch.thetellerandroid.card.ChargeRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/v1.1/transaction/process")
//    Call<ChargeResponse> charge(@Body ChargeRequestBody body);
    Call<String> chargeCard(@Body gh.com.payswitch.thetellerandroid.card.ChargeRequestBody body);

    @Headers("Content-Type: application/json")
    @POST("/v1.1/transaction/process")
//    Call<ChargeResponse> charge(@Body ChargeRequestBody body);
    Call<String> chargeMomo(@Body gh.com.payswitch.thetellerandroid.ghmobilemoney.ChargeRequestBody body);

//    @POST("/flwv3-pug/getpaidx/api/validatecharge")
//    Call<String> validateCardCharge(@Body ValidateChargeBody body);
////    Call<ChargeResponse> validateCardCharge(@Body ValidateChargeBody body);
//
//    @POST("/flwv3-pug/getpaidx/api/validate")
//    Call<String> validateAccountCharge(@Body ValidateChargeBody body);
////    Call<ChargeResponse> validateAccountCharge(@Body ValidateChargeBody body);

//    @POST("/flwv3-pug/getpaidx/api/verify")
//    Call<String> requeryTx(@Body RequeryRequestBody body);
//    Call<RequeryResponse> requeryTx(@Body RequeryRequestBody body);

//    @POST("/flwv3-pug/getpaidx/api/v2/verify")
//    Call<String> requeryTx_v2(@Body RequeryRequestBodyv2 body);

//    @GET("/flwv3-pug/getpaidx/api/flwpbf-banks.js?json=1")
//    Call<List<Bank>> getBanks();

//    @POST("/flwv3-pug/getpaidx/api/tokenized/charge")
//    Call<String> chargeToken(@Body Payload payload);

//    @POST("/v1.1/transaction/process")
//    Call<FeeCheckResponse> checkFee(@Body FeeCheckRequestBody body);

}