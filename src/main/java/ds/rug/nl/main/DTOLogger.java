/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.main;

import ds.rug.nl.network.DTO.DTO;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class DTOLogger {
    
    private Set<Class> typesToLog;
    
    public DTOLogger(){
        this.typesToLog = new HashSet<Class>();
    }
    
    public void registerType(Class c){
        typesToLog.add(c);
    }
    
    public void logMsg(DTO dto){
        
        if(typesToLog.contains(dto.getClass())){
            System.out.print("Logging...");
            System.out.println(dto.toString());
            
        }        
    }
    
    
}
