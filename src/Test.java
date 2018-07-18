import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        System.out.println("开始读取文件...");
        Map<String, String> baseinfos = MyTool.readBaseinfo();
        Map<String, String> marriageinfos = MyTool.readMarriageinfo();
        List<Map<String, List<String>>> childinfos = MyTool.readChildinfo();
        System.out.println("baseinfo.txt文件的结构化内容是:");
        System.out.println(baseinfos);
        System.out.println("childinfo.txt文件的结构化内容是:");
        System.out.println(childinfos);
        System.out.println("marriageinfo.txt文件的结构化内容是:");
        System.out.println(marriageinfos);
        System.out.println("读取文件完毕.");

        System.out.println("开始创建族谱结构...");
        ChildSiblingTree tree = MyTool.createFamilyTree(baseinfos, marriageinfos, childinfos);
        System.out.println("创建族谱结构完毕.");

        MyTool.console(tree, baseinfos, marriageinfos, childinfos);
    }
}
