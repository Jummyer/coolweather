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
        client.newCall(request).enqueue(callback);//采用异步方式生成请求实例，call.execute()，非异步方式，会阻塞线程，等待返回结果
    }
}
