package android.coolweather.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by lym on 2017/12/25.
 */

public class County extends DataSupport {
    private int id;
    private String countyName;//记录每个县的名字
    private String weatherId;//记录每个县所对应的天气Id
    private int cityId;//记录当前县所属的市的Id

    public void setId(int id) {
        this.id = id;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getId() {

        return id;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public int getCityId() {
        return cityId;
    }
}
