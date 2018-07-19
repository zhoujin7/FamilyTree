import java.util.*;
import java.util.Map.Entry;

public class MyTool {
    // 读取baseinfo.txt文件信息, 保存为Map结构
    public static Map<String, String> readBaseinfo() {
        List<String> lines = FileTool.readLines("baseinfo.txt");
        Map<String, String> baseinfos = new HashMap<String, String>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.trim();
            if (!line.equals("")) {
                String[] arr = line.split(" ");
                baseinfos.put(arr[0], arr[1]);
            }
        }
        return baseinfos;
    }

    // 读取childinfo.txt文件信息, 保存为List结构
    public static List<Map<String, List<String>>> readChildinfo() {
        List<String> lines = FileTool.readLines("childinfo.txt");
        List<Map<String, List<String>>> childinfos = new ArrayList<Map<String, List<String>>>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.trim();
            if (!line.equals("")) {
                List<String> childs = new ArrayList<String>();
                String[] arr = line.split(" ");
                for (int j = 1; j < arr.length; j++) {
                    childs.add(arr[j]);
                }
                Map<String, List<String>> map = new HashMap<String, List<String>>();
                map.put(arr[0], childs);
                childinfos.add(map);
            }
        }
        return childinfos;
    }

    // 读取marriageinfo.txt文件信息, 保存为Map结构
    public static Map<String, String> readMarriageinfo() {
        List<String> lines = FileTool.readLines("marriageinfo.txt");
        Map<String, String> marriageinfos = new HashMap<String, String>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.trim();
            if (!line.equals("")) {
                String[] arr = line.split(" ");
                marriageinfos.put(arr[0], arr[1]);
            }
        }
        return marriageinfos;
    }

    // 改变文件行首的数字, 如果文件行数变了, 就给数值加一
    public static void changeNum(String fileName) {
        List<String> contentList = FileTool.readLines(fileName);
        String firstLine = contentList.get(0);
        int prevNum = Integer.parseInt(firstLine);
        int currentNum = ++prevNum;
        contentList.set(0, currentNum + "");
        FileTool.writeLines(fileName, contentList);
    }

    // 创建家谱树
    // 创建家谱树分为两步, 第一步创建根结点和根结点的孩子, 第二步创建根节点孩子的子孙
    public static ChildSiblingTree createFamilyTree(Map<String, String> baseinfos, Map<String, String> marriageinfos,
                                                    List<Map<String, List<String>>> childinfos) {

        // 第一步创建根结点和根结点的孩子
        ChildSiblingTree tree = null;
        // 从childinfos中获取第一个Map元素rootNodeInfo, 也就是childinfo.txt的第二行信息
        Map<String, List<String>> rootNodeInfo = childinfos.get(0);
        Set<Entry<String, List<String>>> entrySet = rootNodeInfo.entrySet();
        // 遍历rootNodeInfo这个Map
        for (Entry<String, List<String>> e : entrySet) {
            // e.getKey()是根节点的id
            // 从baseinfos这个Map里面取出根节点的名字 name
            String name = baseinfos.get(e.getKey());
            // 从marriageinfos这个Map里面取出根节点的配偶信息
            String spouseId = marriageinfos.get(e.getKey());
            String spouseName = "";
            Spouse spouse = null;
            if (spouseId != null && !spouseId.equals("")) {
                // 从baseinfos这个Map里面取出配偶的名字
                spouseName = baseinfos.get(spouseId);
                // 创建一个新的配偶对象
                spouse = new Spouse(spouseId, spouseName);
            }
            // 创建家谱树, 目前还只有树根的根节点和根节点的配偶
            tree = new ChildSiblingTree(e.getKey(), name, spouse);
            // e.getValue()是根节点的孩子id列表
            for (String id : e.getValue()) {
                // 从baseinfos这个Map里面取出孩子的名字
                name = baseinfos.get(id);
                // 从marriageinfos这个Map里面取出孩子的配偶id
                spouseId = marriageinfos.get(id);
                spouse = null;
                if (spouseId != null && !spouseId.equals("")) {
                    // 从baseinfos这个Map里面取出孩子的配偶的名字
                    spouseName = baseinfos.get(spouseId);
                    // 创建一个新的配偶对象
                    spouse = new Spouse(spouseId, spouseName);
                }
                // 给根节点插入他的孩子
                tree.insertChildNode(tree.getRoot(), id, name, spouse);
                tree.getRoot().setDirectChildCount();
            }
        }

        // 第二步创建根节点孩子的子孙
        // 跳过对根节点及他直接孩子结点的处理, 就是跳过childinfos这个List的第一个元素
        int i = 0;
        for (Map<String, List<String>> childinfo : childinfos) {
            if (i > 0) {
                entrySet = childinfo.entrySet();
                for (Entry<String, List<String>> e : entrySet) {
                    for (String id : e.getValue()) {
                        String fatherId = e.getKey();
                        TreeNode father = tree.searchId(fatherId);
                        String name = baseinfos.get(id);
                        String spouseId = marriageinfos.get(fatherId);
                        String spouseName = "";
                        Spouse spouse = null;
                        if (spouseId != null && !spouseId.equals("")) {
                            spouseName = baseinfos.get(spouseId);
                            spouse = new Spouse(spouseId, spouseName);
                        }
                        father.setSpouse(spouse);
                        tree.insertChildNode(father, id, name, null);
                        father.setDirectChildCount();
                    }
                }
            }
            i++;
        }
        return tree;
    }

    public static void console(ChildSiblingTree tree, Map<String, String> baseinfos, Map<String, String> marriageinfos,
                               List<Map<String, List<String>>> childinfos) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0, j = 0; i == 0; ) {
            if (j != 0) {
                System.out.println("请按回车键继续...");
                scanner.nextLine();
                j = 0;
            }
            if (j == 0) {
                System.out.println();
                System.out.println("请选择所要进行的操作: ");
                System.out.println("0 人员信息的录入");
                System.out.println("1 新生孩子的插入");
                System.out.println("2 第几代查询");
                System.out.println("3 父母亲查询");
                System.out.println("4 孩子查询");
                System.out.println("5 祖先查询");
                System.out.println("6 结婚信息插入");
                System.out.println("7 查看结构化文件信息");
                System.out.println("8 退出系统");
                System.out.println();

                int selectedNum = scanner.nextInt();
                scanner.nextLine();
                String id = "";
                switch (selectedNum) {
                    case 0:
                        System.out.println("请输入人员的身份证号码: ");
                        String personId = scanner.nextLine();
                        System.out.println("请输入人员的姓名: ");
                        String personName = scanner.nextLine();
                        FileTool.append("baseinfo.txt", personId + " " + personName);
                        baseinfos.put(personId, personName);
                        MyTool.changeNum("baseinfo.txt");
                        System.out.println("人员信息录入成功!");
                        j = -1;
                        break;
                    case 1:
                        System.out.println("请输入父亲的身份证号码: ");
                        String fatherId = scanner.nextLine();
                        System.out.println("请输入孩子的身份证号码: ");
                        String childId = scanner.nextLine();
                        System.out.println("请输入孩子的姓名: ");
                        String childName = scanner.nextLine();
                        TreeNode father = null;
                        if (fatherId != null && !fatherId.equals("") && childId != null && !childId.equals("")
                                && childName != null && !childName.equals("")) {
                            father = tree.searchId(fatherId);
                            if (father != null && father.getSpouse() != null) {
                                tree.insertChildNode(father, childId, childName, null);
                                FileTool.append("baseinfo.txt", childId + " " + childName);
                                MyTool.changeNum("baseinfo.txt");
                                if (father.getDirectChildCount() == 0) {
                                    FileTool.append("childinfo.txt", fatherId + " " + childId);
                                    MyTool.changeNum("childinfo.txt");
                                    father.setDirectChildCount();
                                } else {
                                    List<String> childinfoLines = FileTool.readLines("childinfo.txt");
                                    StringBuilder sb = new StringBuilder();
                                    String regex = "^" + fatherId;
                                    for (String childinfoLine : childinfoLines) {
                                        childinfoLine = childinfoLine.replaceAll(regex, "$0 " + childId);
                                        sb.append(childinfoLine + FileTool.getNewLine());
                                    }
                                    FileTool.write("childinfo.txt", sb.toString());
                                    father.setDirectChildCount();
                                }
                                System.out.println("新生孩子插入成功.");
                            } else if (father == null) {
                                System.out.println("父亲不存在, 请检查身份证号码是否输入有误.");
                            } else {
                                System.out.println("该男子还没结婚, 请先结婚再生小孩.");
                            }
                        } else {
                            System.out.println("新生孩子插入失败.");
                        }
                        j = -1;
                        break;
                    case 2:
                        System.out.println("请输入身份证号码: ");
                        id = scanner.nextLine();
                        tree.findGeneration(id);
                        j = -1;
                        break;
                    case 3:
                        System.out.println("请输入身份证号码: ");
                        id = scanner.nextLine();
                        tree.findParent(id);
                        j = -1;
                        break;
                    case 4:
                        System.out.println("请输入身份证号码: ");
                        id = scanner.nextLine();
                        tree.findChildren(id);
                        j = -1;
                        break;
                    case 5:
                        System.out.println("请输入身份证号码: ");
                        id = scanner.nextLine();
                        tree.findAncestors(id);
                        j = -1;
                        break;
                    case 6:
                        System.out.println("请输入新郎的身份证号码: ");
                        String bridegroomId = scanner.nextLine();
                        System.out.println("请输入新娘的身份证号码: ");
                        String brideId = scanner.nextLine();
                        String brideName = baseinfos.get(brideId);
                        String marriedMan = marriageinfos.get(bridegroomId);
                        TreeNode bridegroom = tree.searchId(bridegroomId);
                        TreeNode man = tree.searchId(brideId);
                        if (marriedMan == null && bridegroom != null && man == null && brideName != null) {
                            bridegroom.setSpouse(new Spouse(brideId, brideName));
                            FileTool.append("marriageinfo.txt", bridegroom.getId() + " " + brideId);
                            marriageinfos.put(bridegroom.getId(), brideId);
                            MyTool.changeNum("marriageinfo.txt");
                            System.out.println(bridegroom.getName() + "和" + brideName + "结婚了.");
                        } else {
                            System.out.println("结婚失败.");
                        }
                        j = -1;
                        break;
                    case 7:
                        System.out.println("开始读取文件...");
                        baseinfos = MyTool.readBaseinfo();
                        marriageinfos = MyTool.readMarriageinfo();
                        childinfos = MyTool.readChildinfo();
                        System.out.println("baseinfo.txt文件的结构化内容是:");
                        System.out.println(baseinfos);
                        System.out.println("childinfo.txt文件的结构化内容是:");
                        System.out.println(childinfos);
                        System.out.println("marriageinfo.txt文件的结构化内容是:");
                        System.out.println(marriageinfos);
                        System.out.println("读取文件完毕.");
                        j = -1;
                        break;
                    case 8:
                        i = -1;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
