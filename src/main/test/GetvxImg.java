import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GetvxImg {

    public static String catHtml(String url){
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get=new HttpGet(url);
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

    //解析网页源码，抽取里面的商品详细信息，保存到List集合
    public static List<String> parsHtml(String html){
        List<String> list=new ArrayList<String>();

        Document doc = Jsoup.parse(html);

        Elements items = doc.select("img[data-src]");


        for(Element item:items){
            list.add(item.attr("data-src"));
        }


        return list;
    }

    //下载指定地址的图片
    public static void down(String url) throws ClientProtocolException, IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        Random random=new Random();
        Date date = new Date();
        int id=  random.nextInt(100000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

        File file = new File("d:\\pic\\"+simpleDateFormat.format(date)+id+".jpg");
        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);

            }
            fout.flush();
            fout.close();
        } finally {
            System.out.println("图像"+id+".jpg下载完毕");
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }
    public static void main(String[] args) {
        Date date = new Date();


        String url="https://mp.weixin.qq.com/s/8W9I9t_Bq30NucvGv1gZzQ";
        String html=catHtml(url);
        List<String> list = parsHtml(html);
      for(String sp:list){
            try {
                down(sp);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }




}
