package android.coolweather.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;
/**
 * suggestion中的具体数据
 */
//"suggestion":
//    {
//       "comf":
//        {
//            "txt":"白天天气较热，虽然有雨，但任然无法削弱较高气温给人们带来的署意，这种天气会让您感到不很舒服。"
//        }
//        "cw":
//        {
//            "txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"
//        }
//        "sport":
//        {
//            "txt":"有降水，且风力较强，推荐您在室内进行低强度运动；若坚持户外运动，请选择避雨防风的地点"
//        }
//    }


/**
 * Created by lym on 2017/12/26.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash crawash;

    public Sport sport;

    public class Comfort
    {
        @SerializedName("txt")
        public String info;
    }

    public class CarWash
    {
        @SerializedName("txt")
        public String info;
    }

    public class Sport
    {
        @SerializedName("txt")
        public String info;
    }

}
