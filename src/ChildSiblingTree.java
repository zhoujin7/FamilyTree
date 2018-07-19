public class ChildSiblingTree {
    // 根节点
    private TreeNode root;

    private int generationNum = 1;

    public ChildSiblingTree(String rootId, String rootName, Spouse rootSpouse) {
        // 构建树的时候, 先初始化根节点
        root = new TreeNode(null, null, rootId, rootName, null, rootSpouse);
    }

    public TreeNode getRoot() {
        return this.root;
    }

    /**
     * 插入孩子节点. 如果没有孩子节点, 直接插入即可. 但如果已有孩子节点, 把原有的孩子变成现在孩子的兄弟.
     */
    public TreeNode insertChildNode(TreeNode father, String id, String name, Spouse spouse) {
        TreeNode currentChildNode = new TreeNode(null, null, id, name, father, spouse);
        TreeNode prevChildNode = father.getChildNode();
        if (prevChildNode != null) {
            // 先前的孩子先变成现在孩子的兄弟, 然后再添加孩子
            currentChildNode.setSiblingNode(prevChildNode);
        }
        father.setChildNode(currentChildNode);
        return currentChildNode;
    }

    /**
     * 通过遍历整颗树来拿取数据, 通过id身份证, 找到对应的结点, 调用递归方法searchId
     */
    public TreeNode searchId(String id) {
        return this.searchId(root, id);
    }

    /**
     * 使用递归取出值
     */
    private TreeNode searchId(TreeNode node, String id) {
        if (node.getId().equals(id)) {
            return node;
        }
        if (!node.getId().equals(id) && node.getChildNode() != null) {
            return searchId(node.getChildNode(), id);
        }
        if (!node.getId().equals(id) && node.getSiblingNode() != null) {
            return searchId(node.getSiblingNode(), id);
        }
        return null;
    }

    // 查找结点的所有子孙
    // 思路 对结点调用getChildNode方法, 得到他的孩子结点, 如果孩子结点不为null, 通过树的先序遍历方法prevOrder查找所有子孙
    // 如果孩子结点为null, 就显示他没有孩子.
    public void findChildren(String id) {
        TreeNode node = searchId(id);
        if (node != null) {
            TreeNode childNode = node.getChildNode();
            if (childNode != null) {
                System.out.println(node.getName() + "的孩子有: ");
                prevOrder(childNode);
            } else {
                System.out.println(node.getName() + "没有孩子");
            }
        } else {
            System.out.println("身份证号码有误, 请检查!");
        }
        System.out.println();
    }

    private void prevOrder(TreeNode node) {
        if (node != null) {
            System.out.print(node.getId() + " " + node.getName() + "\n");
            prevOrder(node.getChildNode());
            prevOrder(node.getSiblingNode());
        }
    }

    // 查找父母
    // 通过getFather方法得到父亲结点, 对父亲结点调用getSpouse方法得到母亲信息
    public void findParent(String id) {
        TreeNode node = searchId(id);
        TreeNode father = node.getFather();
        if (father != null) {
            System.out.println(node.getName() + "的父亲是: ");
            System.out.println(father.getId() + " " + father.getName());
            System.out.println(node.getName() + "的母亲是: ");
            Spouse mother = father.getSpouse();
            System.out.println(mother.getId() + " " + mother.getName());
        } else {
            System.out.println("身份证号码有误, 请检查!");
        }
        System.out.println();
    }

    // 查找所有祖先
    // 思路 如果结点和根节点相同 ,那么打印没有祖先
    // 如果不同, 通过递归方法showFather打印出所有祖先信息
    public void findAncestors(String id) {
        TreeNode node = searchId(id);
        if (node != null) {
            if (node.equals(root)) {
                System.out.println(node.getName() + "没有祖先");
            } else {
                System.out.println(node.getName() + "的祖先是: ");
                showFather(node);
            }
        } else {
            System.out.println("身份证号码有误, 请检查!");
        }
        System.out.println();
    }

    private void showFather(TreeNode node) {
        if (node.getFather() != null) {
            System.out.println(node.getFather().getId() + " " + node.getFather().getName());
            showFather(node.getFather());
        }
    }

    // 查找第几代
    // 思路: 通过递归countGeneration操作对他的所有祖先进行计数, 如果他有两个祖先, 他就是第3代
    public void findGeneration(String id) {
        TreeNode node = searchId(id);
        if (node != null) {
            countGeneration(node);
            System.out.println(node.getName() + "是第" + generationNum + "代");
            generationNum = 1;
        } else {
            System.out.println("身份证号码有误, 请检查!");
        }
        System.out.println();
    }

    private void countGeneration(TreeNode node) {
        if (node.getFather() != null) {
            generationNum++;
            countGeneration(node.getFather());
        }
    }

}