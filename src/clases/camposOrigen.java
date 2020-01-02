/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author marqu
 */
public class camposOrigen {
    
    static private String hostname;
    static private String username;
    static private String password;
    static private String baseDestino;
    
    

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBaseDestino(String baseDestino) {
        this.baseDestino = baseDestino;
    }

    public String getBaseDestino() {
        return baseDestino;
    }

}
