public class Node {
    

    
    TreeNode<Node> nodesTree;
    InetAddress address;
    NodeID ID;
    
    public Node(address, nodesTree) {
        self.address = address;
        self.nodesTree = nodesTree;
        self.ID = NodeID(address);
    }
    
    // should somehow talk to group manger to ensure different ID on rejoin
  
    public class NodeId extends {
        int ID;
        
        public NodeId(address){
            this.ID = address.hashCode();
        }
    }
}



