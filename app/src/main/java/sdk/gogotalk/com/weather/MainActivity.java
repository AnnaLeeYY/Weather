package sdk.gogotalk.com.weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView cityT, timeT, humidityT, powerT, nowtemperatureT, weekT, temperatureT, climateT, windT;
    private ImageView weatherStateImg;
    private TodayWeather todayWeather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (CheckNet.getNetState(this) == CheckNet.NET_NONE) {
            Toast.makeText(MainActivity.this, "没有网络", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "有网络", Toast.LENGTH_LONG).show();
            getWeatherDatafromNet("101220904");

        }
    }

    void initView() {
        cityT = (TextView) findViewById(R.id.todayinfo1_cityName);
        timeT = (TextView) findViewById(R.id.todayinfo1_updateTime);
        humidityT = (TextView) findViewById(R.id.todayinfo1_humidity);
        powerT = (TextView) findViewById(R.id.wind_power);
        nowtemperatureT = (TextView) findViewById(R.id.now_the_temperature);
        weekT = (TextView) findViewById(R.id.todayinfo2_week);
        temperatureT = (TextView) findViewById(R.id.todayinfo2_temperature);
        climateT = (TextView) findViewById(R.id.todayinfo2_weatherState);
        windT = (TextView) findViewById(R.id.todayinfo2_wind);
        weatherStateImg = (ImageView) findViewById(R.id.todayinfo2_weatherStatusImg);
    }

    private void getWeatherDatafromNet(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("Address:", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                        Log.d("date from url", str);
                    }
                    String response = sb.toString();
                    Log.d("response", response);
                    todayWeather = XML.parseXML(response);
                    if (todayWeather != null) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = todayWeather;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
            }
        }
    };

    void updateTodayWeather(TodayWeather todayWeather) {
        cityT.setText(todayWeather.getCity());
        timeT.setText("发布时间" + todayWeather.getUpdatetime());
        humidityT.setText("湿度:" + todayWeather.getShidu());
        powerT.setText("风力:" + todayWeather.getFengli());
        nowtemperatureT.setText("现在温度" + todayWeather.getWendu() + "℃");
        weekT.setText(todayWeather.getDate());
        temperatureT.setText(todayWeather.getHigh() + "~" + todayWeather.getLow());
        climateT.setText(todayWeather.getType());
        windT.setText(todayWeather.getFengxiang());
        switch (todayWeather.getType()) {
            case "晴":
                weatherStateImg.setBackgroundResource(R.drawable.weather_qing);
                break;
            case "阴":
                weatherStateImg.setBackgroundResource(R.drawable.weather_yin);
                break;
            case "多云":
            case "多云转晴":
            case "晴转多云":
            case "多云转阴":
                weatherStateImg.setBackgroundResource(R.drawable.weather_duoyun);
                break;
            case "雾":
            case "浓雾":
            case "强浓雾":
                weatherStateImg.setBackgroundResource(R.drawable.weather_wu);
                break;
            case "雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_xue);
                break;
            case "雨夹雪":
            case "雨夹雪转多云":
            case "多云转雨夹雪":
            case "阴转雨夹雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_yujiaxue);
                break;
            case "阵雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_zhenxue);
                break;
            case "小雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_xiaoxue);
                break;
            case "中雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_zhongxue);
                break;
            case "大雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_daxue);
                break;
            case "暴雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_baoxue);
                break;
            case "小到中雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_xiaoxuezhuanzhongxue);
                break;
            case "中到大雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_zhongxuezhuandaxue);
                break;
            case "大到暴雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_daxuezhuanbaoxue);
                break;
            case "雨":
            case "多云转小雨":
            case "阴转雨":
            case "雨转多云":
                weatherStateImg.setBackgroundResource(R.drawable.weather_yu);
                break;
            case "阵雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_zhenyu);
                break;
            case "雷阵雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_leizhenyu);
                break;
            case "雷阵雨伴有冰雹":
                weatherStateImg.setBackgroundResource(R.drawable.weather_leiyubingbao);
                break;
            case "小雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_xiaoyu);
                break;
            case "中雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_zhongyu);
                break;
            case "大雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_dayu);
                break;
            case "暴雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_baoyu);
                break;
            case "大暴雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_dabaoyu);
                break;
            case "特大暴雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_tedabaoyu);
                break;
            case "小到中雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_xiaoyuzhuanzhongyu);
                break;
            case "中到大雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_zhongyuzhuandayu);
                break;
            case "大到暴雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_dayuzhuanbaoyu);
                break;
            case "暴雨到大暴雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_baoyuzhuandabaoyu);
                break;
            case "大暴雨到特大暴雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_dabaoyuzhuantedabaoyu);
                break;
            case "霜":
                weatherStateImg.setBackgroundResource(R.drawable.weather_shuang);
            case "冻雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_dongyu);
                break;
            case "浮尘":
                weatherStateImg.setBackgroundResource(R.drawable.weather_fuchen);
                break;
            case "扬沙":
                weatherStateImg.setBackgroundResource(R.drawable.weather_yangsha);
                break;
            case "沙尘暴":
            case "强沙尘暴":
            case "特强沙尘暴轻雾":
                weatherStateImg.setBackgroundResource(R.drawable.weather_tedashachen);
                break;
            case "轻微霾":
            case "中度霾":
            case "重度霾":
            case "特强霾":
                weatherStateImg.setBackgroundResource(R.drawable.weather_wumai);
                break;
            default:
                weatherStateImg.setBackgroundResource(R.drawable.weather_weizhi);
                break;
        }
    }
}
