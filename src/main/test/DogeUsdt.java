import Util.CatHtml;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import modo.Doge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DogeUsdt {

    public static String url = "https://api.huobi.mn/market/history/kline?symbol=dogeusdt";

    public static void main(String[] args) {
        System.out.println("监控开始");
        JSONObject obj = new JSONObject();
        String json = "";
        String message = "";
        List<Doge> objects = new ArrayList<>(155);
        Doge doge = new Doge();
        LocalTime time = LocalTime.now();
        int flag = 0;
        int addflag = 0;
        boolean whileflag = true;
        while (whileflag) {
            json = CatHtml.catHtml(url);
            obj = JSONObject.parseObject(json);
            message = obj.getString("data");
            objects = JSON.parseArray(message, Doge.class);
            remove(objects);
            if (objects!=null){
                doge= objects.get(0);

            }

        }

    }

    public static void remove(List<Doge> doges){

        for (int i = doges.size()-1; i >0 ; i--) {
            if (doges.get(i).getAmount().equals("0.0")){
                doges.remove(i);
            }
        }

    }





}
