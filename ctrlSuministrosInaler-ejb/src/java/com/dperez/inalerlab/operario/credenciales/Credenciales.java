
package com.dperez.inalerlab.operario.credenciales;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Credenciales implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdCredenciales;
    private String PasswordCredenciales;
    private String SaltCredenciales;

    // constructores
    public Credenciales() {}

    public Credenciales(String PasswordCredenciales, String SaltCredenciales) {
        this.PasswordCredenciales = PasswordCredenciales;
        this.SaltCredenciales = SaltCredenciales;
    }
    
    // getters
    public int getIdCredenciales() {return IdCredenciales;}
    public String getPasswordCredenciales() {return PasswordCredenciales;}
    public String getSaltCredenciales() {return SaltCredenciales;}
    
    //  setters
    public void setIdCredenciales(int IdCredenciales) {this.IdCredenciales = IdCredenciales;}
    public void setPasswordCredenciales(String PasswordCredenciales) {this.PasswordCredenciales = PasswordCredenciales;}
    public void setSaltCredenciales(String SaltCredenciales) {this.SaltCredenciales = SaltCredenciales;}
    
    
}