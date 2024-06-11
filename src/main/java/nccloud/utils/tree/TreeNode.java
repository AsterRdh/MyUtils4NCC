package nccloud.utils.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode <E>{
    private String id;
    private String pid;
    private List<TreeNode> child;
    private E self ;
    private int level;

    public TreeNode(String id, String pid,E self, int level) {
        this.id = id;
        this.pid = pid;
        this.child = new ArrayList<>();
        this.self = self;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<TreeNode> getChild() {
        return child;
    }

    public void setChild(List<TreeNode> child) {
        this.child = child;
    }

    public E getSelf() {
        return self;
    }

    public void setSelf(E self) {
        this.self = self;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
