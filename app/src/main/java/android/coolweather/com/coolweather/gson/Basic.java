package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * JSON返回的数据的格式
 */
//{
//    "HeWeather":
//    [
//        {
//            "status":"ok",
//            "basic":{},
//            "aqi":{},
//            "now":{},
//            "suggestion":{},
//            "daily_forecast":[]
//        }
//    ]
//}

/**
 * 返回的JSON数据中basic的具体内容
 */
//"basic":
//        {
//        "city":"苏州",
//        "id":"CN101190401",
//        "update":
//            {
//                "loc":"2016-08-08-21:58"
//            }
//        }

/**
 * 使用@SerializedName注解的方式来让JSON字段和java字段之间建立映射关系
 * Created by lym on 2017/12/26.
 */

public class Basic {
    @SerializedName("city")//城市名
    public String cityName;

    @SerializedName("id")//城市对应的天气id
    public String weatherId;

    public Update update;

    public class Update
    {
        @SerializedName("loc")//天气的更新时间
        public String updateTime;
    }
}
