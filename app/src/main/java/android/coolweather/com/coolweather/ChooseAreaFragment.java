package android.coolweather.com.coolweather;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.coolweather.com.coolweather.db.City;
import android.coolweather.com.coolweather.db.County;
import android.coolweather.com.coolweather.db.Province;
import android.coolweather.com.coolweather.util.HttpUtil;
import android.coolweather.com.coolweather.util.Utility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lym on 2017/12/25.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView tittleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    /**
     * 省列表
     */
    private List<Province> provinceList;

    /**
     * 市列表
     */
    private List<City> cityList;

    /**
     * 县列表
     */
    private List<County> countyList;

    /**
     * 选中的省份
     */
    private Province selectedProvince;

    /**
     * 选中的城市
     */
    private City selectedCity;

    /**
     * 当前选中的级别
     */
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);//将布局填充成View对象
        tittleText = (TextView) view.findViewById(R.id.tittle_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);//实例化一个adapter适配器
        listView.setAdapter(adapter);//将ArrayAdapter设置为listView的适配器
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             * 设置listview点击事件，如果当前在province情况下点击listview，则进入city界面，在city情况下点击则进入county界面
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }
                else if (currentLevel == LEVEL_CITY)
                {
                    selectedCity = cityList.get(position);
                    queryCounties();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * 设置返回Button点击事件
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY)
                {
                    queryCities();
                }else if (currentLevel == LEVEL_CITY)
                {
                    queryProvinces();
                }
            }
        });
        //queryCities();
        queryProvinces();//加载省级数据
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces()
    {
        Log.i("tag","line 141");
        tittleText.setText("中国");//设置头布局为“中国”
        Log.i("tag","line 143");
        backButton.setVisibility(View.GONE);//动态设置返回按钮为隐藏
        Log.i("tag","line 145");
        provinceList = DataSupport.findAll(Province.class);//调用LitePal的查询接口来从数据库中读取省级数据
        Log.i("tag","provinceList.size() = " + provinceList.size());
        if (provinceList.size() > 0)
        {
            //如果从查询接口中读到了数据，就将数据显示到界面
            dataList.clear();
            for (Province province:provinceList)
            {
                dataList.add(province.getProvinceName());
            }
            //更新列表
            adapter.notifyDataSetChanged();//通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容
            listView.setSelection(0);//这个方法的作用就是将第position个item显示在listView的最上面一项
            currentLevel = LEVEL_PROVINCE;
        }
        else
        {
            //如果未能从查询接口中读到数据，则从服务器上查询数据
            String address = "http://guolin.tech/api/china";//此处需要外网
            queryFromServer(address,"province");//传入服务器地址及数据类型，从服务器上查询数据
        }
    }

    /**
     * 查询选中的省的所以市，优先从数据库中查询，如果没有查询到再去服务器上查询
     */
    private void queryCities()
    {
        tittleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0)
        {
            dataList.clear();
            for (City city : cityList)
            {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }
        else
        {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china" + provinceCode;
            queryFromServer(address,"city");
        }
    }

    /**
     * 查询选中的市内的所以县，优先从数据库中查询，如果没有查询到再从服务器上去查询
     */
    private void queryCounties()
    {
        tittleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0)
        {
            dataList.clear();
            for (County county : countyList)
            {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }
        else
        {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china" + provinceCode + "/" + cityCode;
            queryFromServer(address,"county");
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县的相关数据
     */
    private void queryFromServer(String address,final String type)
    {
        showProgressDialog();//显示加载进度条
        //调用HttpUtil的sendOkHttpaRequest()方法向服务器发送请求
        HttpUtil.sendOkHttpaRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            /**
             * 服务器响应的数据会回调到onResponse()方法中
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type))
                {
                    result = Utility.handleProvinceResponse(responseText);//解析和处理服务器返回的数据，并保存到数据库中
                }
                else if ("city".equals(type))
                {
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                }
                else if ("county".equals(type))
                {
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }

                /**
                 * 在解析和处理完数据之后，再次调用queryProvinces()方法来重新加载数据，由于queryProvince()方法牵扯到了UI操作，
                 * 因此必须在主线程里调用，借助了runOnUiThread()方法来实现从子线程切换到主线程
                 */
                if (result)
                {
                    //通过runOnUiThread()方法回到主线程处理逻辑
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();//关闭进度条
                            if ("province".equals(type))
                            {
                                queryProvinces();
                            }
                            else if ("ciry".equals(type))
                            {
                                queryCities();
                            }
                            else if ("county".equals(type))
                            {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog()
    {
        if (progressDialog == null)
        {
            progressDialog  = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog()
    {
        if (progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }
}
