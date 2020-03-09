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
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
    //解析网页源码，抽取里面的图片url，保存到List集合
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
    public static void down(String url,int id) throws  IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd__");
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
            InputStream is = null;
            BufferedImage src = null;
            is = new FileInputStream(file);
            BufferedImage image = null;
            image = javax.imageio.ImageIO.read(is);
            int width =image.getWidth();
            int height = image.getHeight();
            int size = width-height;
            is.close();
            if (Math.abs(size) >50||width<100||height<100) {
                System.out.println("图片大小不正确");
                System.out.println("url:"+url);
                File file2 = new File("d:\\pic2\\"+simpleDateFormat.format(date)+id+".jpg");
                file.renameTo(file2);
            }
        } finally {
            System.out.println("图像"+id+".jpg处理完毕");
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }
    public static int forList(String url, int id  ) {
        String html=catHtml(url);
        List<String> list = parsHtml(html);
        for(String sp:list){
            try {
                down(sp,id);
                id++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
    public static void main(String[] args) throws IOException {
        int id= 1;
        List<String> urllist = new ArrayList<>();
        //在下面输入微信链接
        urllist.add( "" );
        urllist.add( "" );
        urllist.add( "" );
        for (int i = 0; i < urllist.size(); i++) {
            id=forList(urllist.get(i),id);
            System.out.println("——————第"+i+"个已完成——————");
        }
    }




}
