package ds.rug.nl.tree;

import java.util.Iterator;
import java.util.Queue;
import java.util.ArrayDeque;


public class HighestLeafIter<T> implements Iterator<TreeNode<T>> {
    // This Deque will honor depth order via breadth first search
    // (with shallowest nodes appearing nearer to the end)
    Queue<TreeNode<T>> nodeQueue = new ArrayDeque<TreeNode<T>>();
    
    public HighestLeafIter(TreeNode<T> node) {
        this.nodeQueue.add(node);
    }
    
    @Override
    public boolean hasNext() {
        return this.nodeQueue.size() != 0;
    }
    
    @Override
    public TreeNode<T> next() {
        
        while(nodeQueue.size() != 0){
            // depth order of queue ensures first leaf is highest leaf not returned yet
            TreeNode<T> node = nodeQueue.remove();
            if (node.isLeaf())
                return node;
            // add all children at the back of the queue to get breadth-first search
            for (TreeNode<T> child : node.children){
                nodeQueue.add(child);
            }
        }
        // cannot possibly be reached if this.hasNext() but required by Java
        return null;
    }
    
    @Override
    public void remove() {
            throw new UnsupportedOperationException();
    }
}