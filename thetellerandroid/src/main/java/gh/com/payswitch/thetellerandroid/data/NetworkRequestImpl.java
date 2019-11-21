package gh.com.payswitch.thetellerandroid.data;
import android.text.TextUtils;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.thetellerActivity;
import gh.com.payswitch.thetellerandroid.card.ChargeRequestBody;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkRequestImpl implements DataRequest.NetworkRequest {

    private Retrofit retrofit;
    private ApiService service;
    private String baseUrl;
    private String errorParsingError = "An error occurred parsing the error response";

    private ErrorBody parseErrorJson(String errorStr) {

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ErrorBody>() {
            }.getType();
            return gson.fromJson(errorStr, type);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ErrorBody("error", errorParsingError);
        }

    }

    @Override
    public void chargeCard(Payload payload, ChargeRequestBody body, final Callbacks.OnChargeRequestComplete callback) {

        service = createService(ApiService.class, payload.getApiUser(), payload.getApiKey());

//        Call<ChargeResponse> call = service.charge(body);
        Call<String> call = service.chargeCard(body);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ChargeResponse>() {}.getType();
                    ChargeResponse chargeResponse = gson.fromJson(response.body(), type);
                    callback.onSuccess(chargeResponse, response.body());
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        ErrorBody error = parseErrorJson(errorBody);
                        callback.onError(error.getMessage(), errorBody);
                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                        callback.onError("error", errorParsingError);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t.getMessage(), "");
            }
        });

    }

    @Override
    public void chargeMomo(Payload payload, gh.com.payswitch.thetellerandroid.ghmobilemoney.ChargeRequestBody body, final Callbacks.OnChargeRequestComplete callback) {

        service = createService(ApiService.class, payload.getApiUser(), payload.getApiKey());

//        Call<ChargeResponse> call = service.charge(body);
        Call<String> call = service.chargeMomo(body);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ChargeResponse>() {}.getType();
                    ChargeResponse chargeResponse = gson.fromJson(response.body(), type);
                    callback.onSuccess(chargeResponse, response.body());
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        ErrorBody error = parseErrorJson(errorBody);
                        callback.onError(error.getMessage(), errorBody);
                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                        callback.onError("error", errorParsingError);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t.getMessage(), "");
            }
        });

    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String getBaseUrl() {
        return this.baseUrl;
    }

    private ApiService createService(
            Class<ApiService> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null, null);
    }

    private ApiService createService(Class<ApiService> serviceClass, String authToken) {

        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (!httpClient.interceptors().contains(interceptor)) {
                OkHttpClient okHttpClient = httpClient.addInterceptor(interceptor)
                        .addNetworkInterceptor(logging)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS).build();

                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(getBaseUrl() == null ? thetellerActivity.BASE_URL : getBaseUrl())
                            .client(okHttpClient)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }

        return retrofit.create(serviceClass);
    }

}
