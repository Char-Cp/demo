import Util.CatHtml;
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

    //解析网页源码，抽取里面的图片url，保存到List集合
    public static List<String> parsHtml(String html) {
        List<String> list = new ArrayList<String>();
        Document doc = Jsoup.parse(html);
        Elements items = doc.select("img[data-src]");
        for (Element item : items) {
            list.add(item.attr("data-src"));
        }
        return list;
    }

    //下载指定地址的图片
    public static void down(String url, int id) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd__");
        File file = new File("d:\\pic\\" + simpleDateFormat.format(date) + id + ".jpg");
        if (file.length()/1024<20){
            in.close();
            httpclient.close();
            System.out.println("图片"+file.length()/1024+"kb大小不正确删除");
            file.delete();
            return;
        }
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
            BufferedImage image = null;
            System.out.println(file.length());
            int width = 0;
            int height = 0;
            try {
                is = new FileInputStream(file);
                image = javax.imageio.ImageIO.read(is);
                width = image.getWidth();
                height = image.getHeight();
                int size = width - height;
                is.close();
                if (Math.abs(size) > 50) {

                    if (height > 1080) {
                        System.out.println("该图为壁纸");
                        File file2 = new File("d:\\pic2\\" + simpleDateFormat.format(date) + id + ".jpg");
                        file.renameTo(file2);
                    } else {
                        System.out.println("图片大小不正确");
                        File file3 = new File("d:\\pic3\\" + simpleDateFormat.format(date) + id + ".jpg");
                        file.renameTo(file3);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("出错url：" + url);
                is.close();
            }
        } finally {
            System.out.println("图像" + id + ".jpg处理完毕");
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }

    public static int forList(String url, int id) {
        String html = CatHtml.catHtml(url);
        List<String> list = parsHtml(html);
        for (String sp : list) {
            try {
                down(sp, id);
                id++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public static void main(String[] args) throws IOException {
        int id = 1;
        List<String> urllist = new ArrayList<>();
        //在下面输入微信链接
        urllist.add("https://mp.weixin.qq.com/s/PtfUyqjOyJnXMob7k9NtnQ");
        urllist.add("https://mp.weixin.qq.com/s/ZZvowb1uIEPhXUPP98JZEA");
        urllist.add("https://mp.weixin.qq.com/s/eqMrRvutOxiyUWptqxvq6Q");
        urllist.add("https://mp.weixin.qq.com/s/7tHn2oy0F368Ss6zrfWxgQ");
        urllist.add("https://mp.weixin.qq.com/s/qn97fzFCcZdY-NtMVqjIqA");
        urllist.add("https://mp.weixin.qq.com/s/YSzYgpHdR72ZRlfFUWV0vA");
        urllist.add("https://mp.weixin.qq.com/s/Yj-gH70DOC6-G5Ut1Dv-Eg");
        urllist.add("https://mp.weixin.qq.com/s/PfpMq39Es8hhj8YKog2YYg");
        urllist.add("https://mp.weixin.qq.com/s/nrXYvqoBl2oz7ifj2gdYng");
        urllist.add("https://mp.weixin.qq.com/s/kbf0FMgTcXanOBpRoBI2mA");
        urllist.add("https://mp.weixin.qq.com/s/DdMxWFpslIf5xGSv2x8vcw");
        urllist.add("https://mp.weixin.qq.com/s/U9ggKyAPtOFy7Ng6vfhkVw");

        for (int i = 0; i < urllist.size(); i++) {
            id = forList(urllist.get(i), id);
            System.out.println("——————第" + i + "个已完成——————");
        }
    }


}
