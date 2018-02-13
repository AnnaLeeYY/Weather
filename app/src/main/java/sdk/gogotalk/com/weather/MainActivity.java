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
            case "雾":
                weatherStateImg.setBackgroundResource(R.drawable.weather_wu);
                break;
            case "雪":
                weatherStateImg.setBackgroundResource(R.drawable.weather_xue);
                break;
            case "雨":
                weatherStateImg.setBackgroundResource(R.drawable.weather_yu);
                break;
            default:
                weatherStateImg.setBackgroundResource(R.drawable.weather_weizhi);
                break;
        }
    }
}
