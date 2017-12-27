package android.coolweather.com.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lym on 2017/12/25.
 */

public class HttpUtil {

    public static void sendOkHttpaRequest(String address,okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient实例
        Request request = new Request.Builder().url(address).build();//通过传入address，创建一个Request对象来实现HTTP的请求
        client.newCall(request).enqueue(callback);
    }
}
