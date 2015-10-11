
import java.net.InetAddress;


/**
 *
 * @author Bart
 */
public class StreamingNode {

    
    TreeNode<StreamingNode> nodesTree;
    InetAddress address;
    NodeId ID;
    
    public StreamingNode(InetAddress address, TreeNode<StreamingNode> nodesTree) {
        this.address = address;
        this.nodesTree = nodesTree;
        this.ID = NodeId(address);
    }
    
    // should somehow talk to group manger to ensure different ID on rejoin
  
    public class NodeId {
        int ID;
        
        public NodeId(InetAddress address){
            this.ID = address.hashCode();
        }
    }
}



