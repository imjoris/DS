/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.main.CommonClientData;
import ds.rug.nl.main.Node;
import ds.rug.nl.main.NodeInfo;
import ds.rug.nl.main.StreamHandler;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.StreamDTO;
import ds.rug.nl.tree.TreeNode;

/**
 *
 * @author Bart
 */
public class StreamAlgo<T> extends Algorithm{

    private final CommonClientData clientData;
    private final StreamHandler<T> streamHandler;

    public StreamAlgo(Node node, CommonClientData clientData, StreamHandler<T> streamHandler) {
        super(node);
        this.clientData = clientData;
        this.streamHandler = streamHandler;
    }

    public void sendData(T data){
        StreamDTO<T> dto = new StreamDTO(data);
        handleDTO(dto);
    }
    
    @Override
    public void handleDTO(DTO message) {
        for(TreeNode<NodeInfo> childNode: clientData.thisNode.children)
            send(message, childNode.contents);
        
        streamHandler.receiveStream((StreamDTO<T>) message);
    }
    
}
