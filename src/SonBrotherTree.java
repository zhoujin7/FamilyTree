public class SonBrotherTree {
    // 根节点
    private TreeNode root;

    private int generationNum = 1;

    public SonBrotherTree(String rootId, String rootName, Spouse rootSpouse) {
        // 构建树的时候, 先初始化根节点
        root = new TreeNode(null, null, rootId, rootName, null, rootSpouse);
    }

    public TreeNode getRoot() {
        return this.root;
    }

    /**
     * 插入孩子节点. 如果没有孩子节点, 直接插入即可. 但如果已有孩子节点, 把原有的孩子变成现在孩子的兄弟.
     */
    public TreeNode insertSonNode(TreeNode father, String id, String name, Spouse spouse) {
        TreeNode nowSonNode = new TreeNode(null, null, id, name, father, spouse);
        TreeNode prevSonNode = father.getSonNode();
        if (prevSonNode != null) {
            // 先前的孩子先变成现在孩子的兄弟, 然后再添加孩子
            nowSonNode.setBrotherNode(prevSonNode);
        }
        father.setSonNode(nowSonNode);
        return nowSonNode;
    }

    /**
     * 通过遍历整颗树来拿取数据
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
        if (!node.getId().equals(id) && node.getSonNode() != null) {
            return searchId(node.getSonNode(), id);
        }
        if (!node.getId().equals(id) && node.getBrotherNode() != null) {
            return searchId(node.getBrotherNode(), id);
        }
        return null;
    }

    public void findChildren(String id) {
        TreeNode node = searchId(id);
        if (node != null) {
            TreeNode sonNode = node.getSonNode();
            if (sonNode != null) {
                System.out.println(node.getName() + "的孩子有: ");
                PreOrder(sonNode);
            } else {
                System.out.println(node.getName() + "没有孩子");
            }
        } else {
            System.out.println("身份证号码有误, 请检查!");
        }
        System.out.println();
    }

    private void PreOrder(TreeNode node) {
        if (node != null) {
            System.out.print(node.getId() + " " + node.getName() + "\n");
            PreOrder(node.getSonNode());
            PreOrder(node.getBrotherNode());
        }
    }

    public void findParent(String id) {
        TreeNode node = searchId(id);
        TreeNode father = node.getFather();
        System.out.println(node.getName() + "的父亲是: ");
        System.out.println(father.getId() + " " + father.getName());
        System.out.println(node.getName() + "的母亲是: ");
        Spouse mother = father.getSpouse();
        System.out.println(mother.getId() + " " + mother.getName());
        System.out.println();
    }

    public void findAncestors(String id) {
        TreeNode node = searchId(id);
        if (node.equals(root)) {
            System.out.println(node.getName() + "没有祖先");
        } else {
            System.out.println(node.getName() + "的祖先是: ");
            showFather(node);
        }
        System.out.println();
    }

    private void showFather(TreeNode node) {
        if (node.getFather() != null) {
            System.out.println(node.getFather().getId() + " " + node.getFather().getName());
            showFather(node.getFather());
        }
    }

    public void findGeneration(String id) {
        TreeNode node = searchId(id);
        countGeneration(node);
        System.out.println(node.getName() + "是第" + generationNum + "代");
        generationNum = 1;
        System.out.println();
    }

    private void countGeneration(TreeNode node) {
        if (node.getFather() != null) {
            generationNum++;
            countGeneration(node.getFather());
        }
    }

}