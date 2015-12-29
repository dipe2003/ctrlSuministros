
package com.dperez.inalerlab.email;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author dperez
 */
@Entity
public class Propiedad implements Serializable{
    @Id
    @Column(unique=true)
    private String NombrePropiedad;
    private String ValorPropiedad;
    
    public Propiedad(){}
    public Propiedad(String NombrePropiedad, String ValorPropiedad){
        this.NombrePropiedad = NombrePropiedad;
        this.ValorPropiedad = ValorPropiedad;
    }

    //Getters
    public String getNombrePropiedad() {return NombrePropiedad;}
    public String getValorPropiedad() {return ValorPropiedad;}

    //Setters
    public void setNombrePropiedad(String NombrePropiedad) {this.NombrePropiedad = NombrePropiedad;}
    public void setValorPropiedad(String ValorPropiedad) {this.ValorPropiedad = ValorPropiedad;}
    
}
