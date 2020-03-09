import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class test {
    private static String path = "C:/";
    private static String filenameTemp;
    private static String Stime = " 06:";
    private static String Xtime = " 18:";
    private static String Ytime = "time=\"2019-";
    private static String Ftime = "-";
    private static String id = "C:/";
    private static String name = "C:/";
    private static String end = "\" workcode=\"0\" status=\"0\" authority=\"0X55\" card_src=\"from_check\"";

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入月份：");
        int Y = sc.nextInt();
        System.out.println("请输入该月份多少天：");
        int sum = sc.nextInt();
        getTimeStr(Y, sum);

    }


    public static String getTimeStr(int Y, int sum) {
        String string = Ytime;
        if (Y < 10) {
            Ytime += "0";
        }
        string += Y;
        for (int i = 1; i <= sum; i++) {
            getRTimeStr(string, i);
        }
        return "";
    }

    public static void getRTimeStr(String str, int R) {
        Random ra = new Random();
        if (R < 10) {
            str += "-0";
            str += R;
        } else {
            str = str + "-" + R;
        }
        int y = 0;
        int i = 0;
        int aa[] = {1, 1, 3, 3, 1, 2, 2, 1, 1, 2, 2, 3};
        per per1 = new per();
        List<per> pers = new LinkedList<>();
        setdata(pers);
        shuffle1(pers);
        for (per pre1 : pers
        ) {
            int ii = ra.nextInt(11);
            i = i + aa[ii];
            String cc = "";
            if (i < 10) {
                cc = cc + "0" + i;
            } else {
                cc += i;
            }
            getSHTimeStr(str, cc, pre1);
        }
        y = 0;
        i = 0;
        shuffle1(pers);
        for (per pre1 : pers
        ) {
            int ii = ra.nextInt(11);
            i = i + aa[ii];
            String cc = "";
            if (i < 10) {
                cc = cc + "0" + i;
            } else {
                cc += i;
            }
            getXHTimeStr(str, cc, pre1);
        }
    }

    public static void getSHTimeStr(String str, String cc, per per1) {
        Random ra = new Random();
        int c = ra.nextInt(48) + 10;
        str = str + " " + "06:" + cc + ":" + c + "\"";
        str = str + " id=\"" + per1.getId() + "\" name=\"" + per1.getName() + end;
        test.method1(str);

    }

    public static void getXHTimeStr(String str, String cc, per per1) {
        Random ra = new Random();
        int c = ra.nextInt(48) + 9;
        str = str + " " + "18:" + cc + ":" + c + "\"";
        str = str + " id=\"" + per1.getId() + "\" name=\"" + per1.getName() + end;
        test.method1(str);

    }

    public static void setdata(List<per> pers) {
        pers.add(getper("11", "11"));
        pers.add(getper("22", "22"));
        pers.add(getper("33", "33"));
        pers.add(getper("44", "44"));
    }

    public static per getper(String name, String id) {
        per pe = new per();
        pe.setName(name);
        pe.setId(id);
        return pe;
    }

    public static <T> void shuffle1(List<T> list) {
        int size = list.size();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            // 获取随机位置
            int randomPos = random.nextInt(size);
            // 当前元素与随机元素交换
            T temp = list.get(i);
            list.set(i, list.get(randomPos));
            list.set(randomPos, temp);
        }
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
        filenameTemp = path + name + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr 新内容
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filenameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    public static void method1(String str) {
        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File("C:\\你好.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(str);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



