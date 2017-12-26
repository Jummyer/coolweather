package android.coolweather.com.coolweather.gson;

/**
 * aqi中的具体内容
 */
//"aqi":
//    {
//        "city":
//            {
//                "aqi":"44",
//                "pm25":"13"
//            }
//    }

/**
 * Created by lym on 2017/12/26.
 */

public class AQI {
    public AQICity city;

    public class AQICity
    {
        public String aqi;
        public String pm25;
    }
}
