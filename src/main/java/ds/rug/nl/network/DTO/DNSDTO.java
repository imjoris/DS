/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.rug.nl.network.DTO;

import java.util.List;

/**
 *
 * @author joris
 */
public class DNSDTO extends DTO{
    public enum cmdType{
        request,
        response
    }
    public cmdType command;
    public List<String> ips;
}
