
import Util.CatHtml;
import Util.SendYZM;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import modo.goods;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class FtMain {
    public static String goodslisturl = "https://gzgsv.xpshop.cn/vshop_List.aspx?type=ajax&Action=GetProductList&CatID=598&BrandID=0&sid=-1&subcat=0&lp=0&hp=0&k=keyword&OrderSort=1&p=&page=1&Size=12&_=1590486979207";

    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        String json = "";
        String message = "";
        List<goods> objects = new ArrayList<>(3);
        goods good = new goods();
        LocalTime time = LocalTime.now();
        int flag = 0;
        int addflag = 0;
        boolean whileflag = true;
        while (whileflag) {
            json = CatHtml.catHtml(goodslisturl);
            if (json.length() > 100) {
                Document doc = Jsoup.parse(json);
                Elements eles = doc.getElementsByTag("title");
                if (eles.size() == 0) {
                    obj = JSONObject.parseObject(json);
                    message = obj.getString("Product");
                    objects = JSON.parseArray(message, goods.class);
                    flag = objects.size();
                    for (int i = 0; i < flag; i++) {
                        good = objects.get(i);
                        if (good.getProductid().equals("1000233")) {
                            if (good.getStorage().equals("1")) {
                                  //SendYZM.send();    发验证码
                                addflag++;
                                try {
                                    Runtime.getRuntime().exec("C:/Program Files/Windows Media Player/wmplayer.exe C:/yhl.mp3");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                time = LocalTime.now();
                                System.out.println("时间：" + time + "检测有货");
                                if (addflag == 3) {
                                    try {
                                        Runtime.getRuntime().exec("C:/Program Files/Windows Media Player/wmplayer.exe C:/lx.mp3");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    whileflag = false;
                                    addflag = 0;
                                }
                            } else {
                                time = LocalTime.now();
                                System.out.println("时间：" + time + "检测无货");
                                addflag = 0;
                            }
                        }
                    }
                    try {
                        Thread.currentThread().sleep(15000);//毫秒
                    } catch (Exception e) {
                    }
                } else {
                    time = LocalTime.now();
                    System.out.println("时间：" + time + "检测网站502");
                    try {
                        Thread.currentThread().sleep(15000);//毫秒
                    } catch (Exception e) {
                    }
                }
            } else {
                time = LocalTime.now();
                System.out.println("时间：" + time + "检测网站502");
                try {
                    Thread.currentThread().sleep(15000);//毫秒
                } catch (Exception e) {
                }
            }
        }
        try {
            Runtime.getRuntime().exec("C:/Program Files/Windows Media Player/wmplayer.exe C:/lx.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
