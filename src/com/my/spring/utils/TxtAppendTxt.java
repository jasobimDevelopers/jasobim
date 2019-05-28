package com.my.spring.utils;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月5日 上午11:09:56
* 类说明
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TxtAppendTxt {

    public static void writeFile(String strSrc, String dest) {
        FileWriter writer = null;
        //FileReader reader = new ;
        InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(strSrc), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            File file = new File(dest);
            if (!file.exists()) {
                file.createNewFile();
            }
            
            // 这里的true，是以追加的方式写
            writer = new FileWriter(dest, true);

            //reader = new FileReader(strSrc);

            String str = null;

            br = new BufferedReader(isr);

            bw = new BufferedWriter(writer);

            while ((str = br.readLine()) != null) {
                StringBuffer sb = new StringBuffer("");

                sb.append(str + "\n");
                bw.write(sb.toString());
            }

            br.close();
            isr.close();

            bw.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //TxtAppendTxt.writeFile("C:/Users/Han/Desktop/txtTest/contentList.txt", "C:/Users/Han/Desktop/txtTest/head.txt");
        //TxtAppendTxt.writeFile("C:/Users/Han/Desktop/txtTest/body.txt", "C:/Users/Han/Desktop/txtTest/head.txt");
        //TxtAppendTxt.writeFile("C:/Users/Han/Desktop/txtTest/approveContentList.txt", "C:/Users/Han/Desktop/txtTest/head.txt");
       // TxtAppendTxt.writeFile("C:/Users/Han/Desktop/txtTest/test2.txt", "C:/Users/Han/Desktop/txtTest/test1.txt");
        //指定复制替换的文件类型
        copy("C:/Users/Han/Desktop/txtTest/test1.txt", "C:/Users/Han/Desktop/txtTest/test1.xml");
        //TxtAppendTxt.writeFile("D:/2.txt", "D:/3.txt");
    }
    /**
     * 复制文件
     *
     * @param oldPath 需要复制的文件路径
     * @param newPath 复制后的文件路劲
     */
    public static void copy(String oldPath, String newPath) {
        try {
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fileOfutputStream = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inStream.read(buffer)) != -1) {
                    fileOfutputStream.write(buffer, 0, length);
                }
                inStream.close();
                fileOfutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

