
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ftpcdemo {
     public static String goodslisturl ="https://gzgsv.xpshop.cn/vshop_List.aspx?type=ajax&Action=GetProductList&CatID=598&BrandID=0&sid=-1&subcat=0&lp=0&hp=0&k=keyword&OrderSort=1&p=&page=1&Size=12&_=1590486979207";
    public static String mtturl ="https://gzgsv.xpshop.cn/vshop_List.aspx?type=ajax&Action=GetProductList&CatID=607&BrandID=0&sid=-1&subcat=0&lp=0&hp=0&k=keyword&OrderSort=1&p=&page=1&Size=12&_=1590487185217";

    public static String catHtml(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response;
        try {
            response = client.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String json = catHtml(goodslisturl);
         JSONObject obj = JSONObject.parseObject(json);
        String message = obj.getString("Product");
        List<goods> objects = JSON.parseArray(message, goods.class);
         for (goods good:objects) {
           if (good.getProductid().equals("1000233")){
               if (good.getStorage().equals("1")){
                   System.out.printf("1111111111");
               }
           }
        }



    }
}
