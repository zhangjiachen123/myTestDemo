package approve.integrate;


import com.ymkj.ams.common.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Zhang jc
 * @date: 2018/9/21 9:55
 * @description: 爬虫
 */
public class RepTileTest {

    private static final String filePath = "E:\\imgeTest";

    private static int count = 1;

    public List<String> repTileGo(String url) {
        Document document = null;
        List imgUrList = new ArrayList();
        try {
            document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByTag("img");
            for (Element element : elements) {
                String imgUrl = element.attr("data-src");
                System.out.println("爬到图片地址:" + imgUrl);
                imgUrList.add(imgUrl);
            }
        } catch (IOException e) {
            System.out.println("卧槽,爬虫爬GG了");
        }
        return imgUrList;
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/21 13:37
     * @description: 计算下一页
     */
    public String nextUrl(String url) {
        //最后一次出现的下标
        int lastIndex = url.lastIndexOf("-") + 1;
        int total = url.length();
        //拿到页数参数
        String lastPage = url.substring(lastIndex, total);
        //下一页 默认第一页
        String nextPage = "";
        if (StringUtils.isNotEmpty(lastPage)) {
            //计算下一页
            nextPage = String.valueOf(Integer.valueOf(lastPage) + 1);
        }
        //拼接url地址
        StringBuilder sb = new StringBuilder(url);
        StringBuilder nextUlr = sb.replace(lastIndex, total, nextPage);
        System.out.println(nextUlr);
        return nextUlr.toString();
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/21 14:33
     * @description: 下载图片
     */
    public static void downLoadImage(String imgUrl) {
        if (StringUtils.isEmpty(imgUrl)) {
            return;
        }
        String fileName = "guoqing" + count;
        count++;

        String mkdirPath = filePath + "\\" + fileName + ".jpg";
        File imgMkdir = new File(filePath);
        if (!createMkdir(imgMkdir)) {
            System.out.println("文件创建失败!");
            return;
        }
        System.out.println("文件创建成功!");
        try {
            URL url = new URL(imgUrl);
            InputStream inputStream = url.openConnection().getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(mkdirPath));
            byte[] bs = new byte[1024];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                fileOutputStream.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static boolean createMkdir(File file) {
        try {
            if (file.exists()) {
                System.out.println("文件已经存在!");
                return true;
            }
            FileUtils.forceMkdir(file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        //初始化url
        String url = "http://www.51yuansu.com/search/guoqing-0-0-0-0-0";
        int count = 0;
        RepTileTest repTile = new RepTileTest();
        //保存所有爬到的图片地址
        List<String> list = new ArrayList<>();
        while (count <= 227) {
            count++;
            url = repTile.nextUrl(url);
            List<String> partList = repTile.repTileGo(url);
            for (String part : partList) {
                list.add(part);
            }
        }
        if (list.isEmpty()) {
            return;
        }
        for (String imgUrl : list) {
            System.out.println("开始下载图片，图片地址:" + imgUrl);
            RepTileTest.downLoadImage(imgUrl);
            System.out.println("下载图片结束=======");
        }
    }


}
