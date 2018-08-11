package com.zhoujin7;

//树的结点
public class TreeNode {
    // 最左的孩子节点
    private TreeNode childNode;
    // 自己的兄弟节点
    private TreeNode siblingNode;
    // 身份证
    private String id;
    // 姓名
    private String name;
    // 父亲
    private TreeNode father;
    // 配偶
    private Spouse spouse;
    // 自己生的孩子个数
    private int directChildCount;

    // 构造函数
    public TreeNode(TreeNode childNode, TreeNode siblingNode, String id, String name, TreeNode father, Spouse spouse) {
        this.childNode = childNode;
        this.siblingNode = siblingNode;
        this.id = id;
        this.name = name;
        this.father = father;
        this.spouse = spouse;
    }

    public TreeNode getChildNode() {
        return this.childNode;
    }

    // 以下是set和get方法用来操作属性值
    public void setChildNode(TreeNode childNode) {
        this.childNode = childNode;
    }

    public TreeNode getSiblingNode() {
        return siblingNode;
    }

    public void setSiblingNode(TreeNode siblingNode) {
        this.siblingNode = siblingNode;
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

    public void setSpouse(Spouse spouse) {
        this.spouse = spouse;
    }

    public void setDirectChildCount() {
        ++this.directChildCount;
    }

    public int getDirectChildCount() {
        return directChildCount;
    }

}