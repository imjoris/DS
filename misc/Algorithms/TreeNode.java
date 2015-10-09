import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;


public class TreeNode<T> {

    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }
    
    // depth first search
    public TreeNode<T> findTreeNode(T item) {
        for (TreeNode<T> child : this.children) {
            if (item.equals(child.data))
                return child;
            
            TreeNode<T> recursiveResult = child.findTreeNode(item);
            if (recursiveResult != null)
                return recursiveResult;
        }
        return null;            
    }
    
    public Iterator<TreeNode<T>> getHighestLeaves() {
        return new HighestLeafIter<T>(this);
    }
    
    public boolean acyclic(){
        Set<TreeNode<T>> seen = new HashSet<TreeNode<T>>();
        return this.acyclic(seen);
    }    
    boolean acyclic(Set<TreeNode<T>> seen) {
        for (TreeNode<T> child : this.children) {
            if (seen.contains(child))
                return false;
            seen.add(child);
            if (!child.acyclic(seen))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
            return data != null ? data.toString() : "[data null]";
    }
}