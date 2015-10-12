/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

/**
 *
 * @author joris
 */
public class JoinDTO extends DTO{
    public enum cmdType{
        getinfo,
        list,
        tree,
        requestjoin,
    }
    cmdType command;
    String message;

    public JoinDTO(cmdType type){
            this.command = type;
    }
    public cmdType getCommand() {
        return command;
    }

    public void setCommand(cmdType command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
