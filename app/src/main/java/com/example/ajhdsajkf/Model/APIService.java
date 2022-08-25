package com.example.ajhdsajkf.Model;

import com.example.ajhdsajkf.Notification.MyResponse;
import com.example.ajhdsajkf.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAXu35cX8:APA91bHFQjOvlz4OicfhDtq5RSw4LnONNuytFV6pdoQrHjOE6buMgl2sSyiD8YFgaWQertzHJk6o1CpGSDJarJWiRmcdGbhjB0FHrHRYbDPN3UL4jwY8bHho2XlieLuxvo79adz2e2VK"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
