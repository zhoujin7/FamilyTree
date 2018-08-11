package com.zhoujin7;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//文件工具 要处理的文件都是GBK编码
public class FileTool {
    //换行符
    private static String newLine = System.getProperty("line.separator");

    //获取换行符
    public static String getNewLine() {
        return newLine;
    }

    //读取文件每行, 保存为List<String>, List的每个元素就是一行
    public static List<String> readLines(String fileName) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        List<String> lines = new ArrayList<String>();
        Reader fr = null;
        BufferedReader br = null;
        try {
            fr = new InputStreamReader(new FileInputStream(file), "GBK");
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return lines;
    }

    //将List<String>写入文件, List的每个元素就是一行
    public static void writeLines(String fileName, List<String> lines) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        OutputStreamWriter fw = null;
        try {
            fw = new OutputStreamWriter(new FileOutputStream(
                    file), "GBK");
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line + FileTool.getNewLine());
            }
            fw.write(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //写文件方法, isAppend表示内容是否为追加
    private static void write(String fileName, String content, boolean isAppend) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        OutputStreamWriter fw = null;
        try {
            fw = new OutputStreamWriter(new FileOutputStream(
                    file, isAppend), "GBK");
            fw.append(content + FileTool.getNewLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //写文件方法, 覆盖以前的内容
    public static void write(String fileName, String content) {
        write(fileName, content, false);
    }

    //写文件方法, 新的内容追加到以后内容的后面
    public static void append(String fileName, String content) {
        write(fileName, content, true);
    }
}
