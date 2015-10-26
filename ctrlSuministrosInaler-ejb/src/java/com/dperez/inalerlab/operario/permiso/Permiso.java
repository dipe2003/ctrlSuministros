
package com.dperez.inalerlab.operario.permiso;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Permiso implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdPermiso;
    @Column(unique = true)
    private String NombrePermiso;
    
    //  constructores
    public Permiso() {}
    public Permiso(String NombrePermiso) {
        this.NombrePermiso = NombrePermiso;
    }
    
    // getters
    public int getIdPermiso() {return IdPermiso;}
    public String getNombrePermiso() {return NombrePermiso;}
    
    //  setters
    public void setIdPermiso(int IdPermiso) {this.IdPermiso = IdPermiso;}
    public void setNombrePermiso(String NombrePermiso) {this.NombrePermiso = NombrePermiso;}
    
}
