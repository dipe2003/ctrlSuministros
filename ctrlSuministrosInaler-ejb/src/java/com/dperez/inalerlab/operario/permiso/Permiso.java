
package com.dperez.inalerlab.operario.permiso;

import com.dperez.inalerlab.operario.Operario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Permiso implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdPermiso;
    @Column(unique = true)
    private String NombrePermiso;
    @OneToMany(mappedBy = "PermisoOperario")
    private List<Operario> OperariosPermiso;
    
    //  constructores
    public Permiso() {}
    public Permiso(String NombrePermiso) {
        this.NombrePermiso = NombrePermiso;
        this.OperariosPermiso = new ArrayList<>();
    }
    
    // getters
    public int getIdPermiso() {return IdPermiso;}
    public String getNombrePermiso() {return NombrePermiso;}
    public List<Operario> getOperariosPermiso() {return OperariosPermiso;}
    
    //  setters
    public void setIdPermiso(int IdPermiso) {this.IdPermiso = IdPermiso;}
    public void setNombrePermiso(String NombrePermiso) {this.NombrePermiso = NombrePermiso;}
    public void setOperarioPermiso(Operario OperarioPermiso) {
        this.OperariosPermiso.add(OperarioPermiso);
        if (!OperarioPermiso.getPermisoOperario().equals(this)) {
            OperarioPermiso.setPermisosOperario(this);
        }
    }
    
}
