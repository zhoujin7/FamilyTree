public class TreeNode {
    // 最左的孩子节点
    private TreeNode sonNode;
    // 自己的兄弟节点
    private TreeNode brotherNode;
    // 身份证
    private String id;
    // 姓名
    private String name;
    // 父亲
    private TreeNode father;
    // 配偶
    private Spouse spouse;
    // 自己生的孩子个数
    private int sonCount;

    public TreeNode(TreeNode sonNode, TreeNode brotherNode, String id, String name, TreeNode father, Spouse spouse) {
        this.sonNode = sonNode;
        this.brotherNode = brotherNode;
        this.id = id;
        this.name = name;
        this.father = father;
        this.spouse = spouse;
        if (sonNode != null) {
            sonCount++;
        }
    }

    public void setSonNode(TreeNode sonNode) {
        this.sonNode = sonNode;
    }

    public void setBrotherNode(TreeNode brotherNode) {
        this.brotherNode = brotherNode;
    }

    public void setSpouse(Spouse spouse) {
        this.spouse = spouse;
    }

    public TreeNode getSonNode() {
        return this.sonNode;
    }

    public TreeNode getBrotherNode() {
        return brotherNode;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TreeNode getFather() {
        return father;
    }

    public Spouse getSpouse() {
        return spouse;
    }

    public int getSonCount() {
        return sonCount;
    }

}