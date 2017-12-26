package android.coolweather.com.coolweather.gson;

/**
 * now中的具体内容
 */
//"now":
//    {
//        "tmp":"29",
//        "cond":
//        {
//            "txt":"阵雨"
//        }
//    }

import com.google.gson.annotations.SerializedName;

/**
 * Created by lym on 2017/12/26.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;
    public class More
    {
        @SerializedName("txt")
        public String info;
    }

}
