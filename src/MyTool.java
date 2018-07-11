import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ReUtil;

public class MyTool {
    public static Map<String, String> readBaseinfo() {
        URL url = MyTool.class.getClassLoader().getResource("baseinfo.txt");
        File file = new File(url.getFile());
        FileReader fileReader = new FileReader(file, "GBK");
        Map<String, String> baseinfos = new HashMap<String, String>();
        List<String> lines = fileReader.readLines();
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

    public static List<Map<String, List<String>>> readChildinfo() {
        URL url = MyTool.class.getClassLoader().getResource("childinfo.txt");
        File file = new File(url.getFile());
        FileReader fileReader = new FileReader(file, "GBK");
        List<String> lines = fileReader.readLines();
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

    public static Map<String, String> readMarriageinfo() {
        URL url = MyTool.class.getClassLoader().getResource("marriageinfo.txt");
        File file = new File(url.getFile());
        FileReader fileReader = new FileReader(file, "GBK");
        List<String> lines = fileReader.readLines();
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

    public static void changeNum(String fileName) {
        URL url = MyTool.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        FileReader fileReader = new FileReader(file, "GBK");
        List<String> contentList = fileReader.readLines();
        String firstLine = contentList.get(0);
        int prevNum = Integer.parseInt(firstLine);
        int currentNum = ++prevNum;
        contentList.set(0, currentNum + "");
        FileWriter fileWriter = new FileWriter(fileName, "GBK");
        fileWriter.writeLines(contentList);
    }

    public static SonBrotherTree createFamilyTree(Map<String, String> baseinfos, Map<String, String> marriageinfos,
            List<Map<String, List<String>>> childinfos) {
        SonBrotherTree tree = null;
        Map<String, List<String>> rootNodeInfo = childinfos.get(0);
        Set<Entry<String, List<String>>> entrySet = rootNodeInfo.entrySet();
        for (Entry<String, List<String>> e : entrySet) {
            String name = baseinfos.get(e.getKey());
            String spouseId = marriageinfos.get(e.getKey());
            String spouseName = "";
            Spouse spouse = null;
            if (spouseId != null && !spouseId.equals("")) {
                spouseName = baseinfos.get(spouseId);
                spouse = new Spouse(spouseId, spouseName);
            }
            tree = new SonBrotherTree(e.getKey(), name, spouse);
            for (String id : e.getValue()) {
                name = baseinfos.get(id);
                spouseId = marriageinfos.get(e.getKey());
                spouse = null;
                if (spouseId != null && !spouseId.equals("")) {
                    spouseName = baseinfos.get(spouseId);
                    spouse = new Spouse(spouseId, spouseName);
                }
                tree.insertSonNode(tree.getRoot(), id, name, spouse);
            }
        }

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
                        tree.insertSonNode(father, id, name, null);
                    }
                }
            }
            i++;
        }
        return tree;
    }

    public static void console(SonBrotherTree tree, Map<String, String> baseinfos, Map<String, String> marriageinfos,
            List<Map<String, List<String>>> childinfos) {
        for (;;) {
            System.out.println();
            System.out.println("请选择所要进行的操作: ");
            System.out.println("0 人员信息的录入");
            System.out.println("1 新生孩子的插入");
            System.out.println("2 第几代查询");
            System.out.println("3 父母亲查询");
            System.out.println("4 孩子查询");
            System.out.println("5 祖先查询");
            System.out.println("6 结婚信息插入");
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            int selectedNum = scanner.nextInt();
            scanner.nextLine();
            String id = "";
            switch (selectedNum) {
            case 0:
                System.out.println("请输入人员的身份证号码和姓名: ");
                String personId = scanner.nextLine();
                String personName = scanner.nextLine();
                URL url = Test.class.getClassLoader().getResource("baseinfo.txt");
                File file = new File(url.getFile());
                FileWriter fileWriter = new FileWriter(file, "GBK");
                fileWriter.append(personId + " " + personName + "\r\n");
                baseinfos.put(personId, personName);
                MyTool.changeNum("baseinfo.txt");
                System.out.println("人员信息录入成功!");
                break;
            case 1:
                System.out.println("请输入父亲的身份证号码: ");
                String fatherId = scanner.nextLine();
                System.out.println("请输入孩子的身份证号码和姓名: ");
                String childId = scanner.nextLine();
                String childName = scanner.nextLine();
                TreeNode father = null;
                if (fatherId != null && !fatherId.equals("") && childId != null && !childId.equals("")
                        && childName != null && !childName.equals("")) {
                    father = tree.searchId(fatherId);
                    if (father != null) {
                        tree.insertSonNode(father, childId, childName, null);
                        url = Test.class.getClassLoader().getResource("baseinfo.txt");
                        file = new File(url.getFile());
                        fileWriter = new FileWriter(file, "GBK");
                        fileWriter.append(childId + " " + childName + "\r\n");
                        MyTool.changeNum("baseinfo.txt");
                        url = Test.class.getClassLoader().getResource("childinfo.txt");
                        file = new File(url.getFile());
                        fileWriter = new FileWriter(file, "GBK");
                        if (father.getSonCount() == 0) {
                            fileWriter.append(fatherId + " " + childId + "\r\n");
                            MyTool.changeNum("childinfo.txt");
                        } else {
                            FileReader fileReader = new FileReader(file);
                            String childinfoContent = fileReader.readString();
                            String findContent = ReUtil.findAll("^" + fatherId, childinfoContent, 0).get(0);
                            ReUtil.replaceAll(childinfoContent, findContent, findContent + " " + childId);
                            childinfos = MyTool.readChildinfo();
                        }
                        System.out.println("新生孩子插入成功.");
                    } else {
                        System.out.println("父亲不存在, 请检查身份证号码是否输入有误.");
                    }
                } else {
                    System.out.println("新生孩子插入失败.");
                }
                break;
            case 2:
                System.out.println("请输入身份证号码: ");
                id = scanner.nextLine();
                tree.findGeneration(id);
                break;
            case 3:
                System.out.println("请输入身份证号码: ");
                id = scanner.nextLine();
                tree.findParent(id);
                break;
            case 4:
                System.out.println("请输入身份证号码: ");
                id = scanner.nextLine();
                tree.findChildren(id);
                break;
            case 5:
                System.out.println("请输入身份证号码: ");
                id = scanner.nextLine();
                tree.findAncestors(id);
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
                    url = Test.class.getClassLoader().getResource("marriageinfo.txt");
                    file = new File(url.getFile());
                    fileWriter = new FileWriter(file, "GBK");
                    fileWriter.append(bridegroom.getId() + " " + brideId + "\r\n");
                    marriageinfos.put(bridegroom.getId(), brideId);
                    MyTool.changeNum("marriageinfo.txt");
                    System.out.println(bridegroom.getName() + "和" + brideName + "结婚了.");
                } else {
                    System.out.println(bridegroom.getName() + "和" + brideName + "结婚失败.");
                }
                break;
            default:
                break;
            }
        }
    }
}
