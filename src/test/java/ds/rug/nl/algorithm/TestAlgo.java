/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.algorithm;

import ds.rug.nl.main.Node;
import ds.rug.nl.network.DTO.DTO;
import ds.rug.nl.network.DTO.TestDTO;
import java.net.InetAddress;

/**
 *
 * @author angelo
 */
public class TestAlgo extends Algorithm{
    private TestDTO testdto;

    public TestAlgo(Node node) {
        super(node);
    }
    
    public String getTestingDTOmsg(){
        return testdto.testString;
    }

    @Override
    public void handleDTO(DTO message) {
        testdto = (TestDTO) message; 
    }
    
    @Override
    public void send(DTO data, InetAddress add, int port){
        super.send(data, add, port);
    }
    
}
