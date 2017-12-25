package android.coolweather.com.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by lym on 2017/12/25.
 */

public class City extends DataSupport {
    private int id;
    private String cityName;//记录每个城市名字
    private int cityCode;//记录每个城市的代号
    private int provinceId;//记录当前城市所属的省的Id

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
