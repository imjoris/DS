package ds.rug.nl.tree;

import ds.rug.nl.main.NodeInfo;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A simple tree structure
 *
 * @author Bart
 * @param <T> Data in the node
 */
public class TreeNode<T> implements java.io.Serializable {

    public T contents;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    /**
     * check if this is a root node.
     *
     * @return Whether this node is a root (has parents)
     */
    public boolean isRoot() {
        return parent == null;
    }

    public boolean containsNode(NodeInfo nodeInfo) {
        TreeNode<T> rootNode = this.getRoot();
        return innerContainsrRecursive(rootNode, nodeInfo);

    }

    private boolean innerContainsrRecursive(TreeNode<T> node, NodeInfo nodeToSearch) {
        if (node == null) {
            return false;
        }
        NodeInfo contentInfo = (NodeInfo) node.contents;
        if (contentInfo.getIpAddress().equals(nodeToSearch.getIpAddress())) {
            return true;
        }
        for (TreeNode<T> child : node.children) {
            if (innerContainsrRecursive(child, nodeToSearch)) {
                return true;
            }
        }
        return false;
    }

    public TreeNode<T> getRoot() {
        TreeNode<T> node;
        node = this;
        while (!node.isRoot()) {
            node = node.parent;
        }
        return node;
    }

    /**
     * check if this is a leaf node
     *
     * @return Whether this node has children
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * Create a new root node with contents as its contents
     *
     * @param data
     */
    public TreeNode(T data) {
        this.contents = data;
        this.children = new ArrayList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);

        if (!(this.containsNode((NodeInfo) child))) {
            childNode.parent = this;
            this.children.add(childNode);
        }

        return childNode;
    }

    /**
     *
     * @return the distance from this node to its root
     */
    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }

    /**
     * Find item through a depth first search
     *
     * @param item the item to find
     * @return the node containing item
     */
    public TreeNode<T> findTreeNode(T item) {
        if (item.equals(this.contents)) {
            return this;
        }
        for (TreeNode<T> child : this.children) {
            if (item.equals(child.contents)) {
                return child;
            }

            TreeNode<T> recursiveResult = child.findTreeNode(item);
            if (recursiveResult != null) {
                return recursiveResult;
            }
        }
        return null;
    }

    /**
     * Get a list of all leaves ordered by their distance from the root (closest
     * first)
     *
     * @return list of highest leaves
     */
    public Iterator<TreeNode<T>> getHighestLeaves() {
        return new HighestLeafIter<T>(this);
    }

    /**
     * Check if the tree actually is a tree (i.e. has no cycles)
     *
     * @return whether this is a tree
     */
    public boolean acyclic() {
        Set<TreeNode<T>> seen = new HashSet<TreeNode<T>>();
        return this.acyclic(seen);
    }

    // actual implementation for recursive method
    boolean acyclic(Set<TreeNode<T>> seen) {
        for (TreeNode<T> child : this.children) {
            if (seen.contains(child)) {
                return false;
            }
            seen.add(child);
            if (!child.acyclic(seen)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return contents != null ? contents.toString() : "[data null]";
    }
}
