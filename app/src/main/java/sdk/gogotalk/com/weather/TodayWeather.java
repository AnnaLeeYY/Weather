package sdk.gogotalk.com.weather;

/**
 * Created by 0 on 2018/2/13.
 */

public class TodayWeather {
    private String city;
    private String updatetime;
    private String wendu;
    private String fengli;
    private String shidu;
    private String fengxiang;
    private String date;
    private String high;
    private String low;

    @Override
    public String toString() {
        return "TodayWeather{" + "city='" + city + '\'' + ", updatetime='" + updatetime + '\'' + ", wendu='" + wendu + '\'' + ", fengli='" + fengli + '\'' + ", shidu='" + shidu + '\'' + ", fengxiang='" + fengxiang + '\'' + ", date='" + date + '\'' + ", high='" + high + '\'' + ", low='" + low + '\'' + ", type='" + type + '\'' + '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
}
