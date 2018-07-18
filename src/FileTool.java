import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileTool {
    private static String newLine = System.getProperty("line.separator");

    public static String getNewLine() {
        return newLine;
    }

    public static List<String> readLines(String fileName) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        List<String> lines = new ArrayList<>();
        try (Reader fr = new InputStreamReader(new FileInputStream(file), "GBK")) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    public static String readString(String fileName) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        StringBuilder sb = new StringBuilder();
        try (Reader fr = new InputStreamReader(new FileInputStream(file), "GBK")) {
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    public static void writeLines(String fileName, List<String> lines) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(
                file), "GBK")) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line + FileTool.getNewLine());
            }
            sb.append(FileTool.getNewLine());
            fw.write(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void append(String fileName, String content) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(
                file), "GBK")) {
            fw.append(content + FileTool.getNewLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
