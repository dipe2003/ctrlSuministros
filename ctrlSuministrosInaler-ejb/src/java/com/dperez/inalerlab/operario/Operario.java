package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.permiso.Permiso;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Salida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Operario implements Serializable{
    @Id
    private int IdOperario;
    private String NombreOperario;
    private String ApellidoOperario;
    private String PasswordKeyOperario;
    private String PasswordOperario;
    @OneToMany(mappedBy = "OperarioIngresoSuministro" )
    private List<Ingreso> IngresosSuministrosOperario;
    @OneToMany(mappedBy = "OperarioSalidaSuministro")
    private List<Salida> SalidasSuministrosOperario;
    @OneToMany
    private List<Permiso> PermisosOperario;
    
    //	Constructores
    public Operario(){}
    public Operario(int IdOperario, String NombreOperario, String ApellidoOperario, String PasswordKeyOperario, String PasswordOperario){
        this.IdOperario = IdOperario;
        this.NombreOperario = NombreOperario;
        this.ApellidoOperario = ApellidoOperario;
        this.PasswordKeyOperario = PasswordKeyOperario;
        this.PasswordOperario = PasswordOperario;
        this.IngresosSuministrosOperario = new ArrayList<>();
        this.IngresosSuministrosOperario = new ArrayList<>();
        this.PermisosOperario = new ArrayList<>();
    }
    
    //	Getters
    public int getIdOperario(){return this.IdOperario;}
    public String getNombreOperario(){return this.NombreOperario;}
    public String getApellidoOperario(){return this.ApellidoOperario;}
    public List<Permiso> getPermisosOperario(){return this.PermisosOperario;}    
    public List<Ingreso> getIngresosSuministrosOperario() {return IngresosSuministrosOperario;}    
    public List<Salida> getSalidasSuministrosOperario() {return SalidasSuministrosOperario;}
    public String getPasswordKeyOperario() {return PasswordKeyOperario;}
    public String getPasswordOperario() {return PasswordOperario;}
    
    //	Setters
    public void setIdOperario(int IdOperario){this.IdOperario = IdOperario;}
    public void setNombreOperario(String NombreOperario){this.NombreOperario = NombreOperario;}
    public void setApellidoOperario(String ApellidoOperario){this.ApellidoOperario = ApellidoOperario;}
    public void setPermisosOperario(List<Permiso> PermisosOperario){this.PermisosOperario = PermisosOperario;}
    public void setIngresosSuministrosOperario(List<Ingreso> IngresosSuministrosOperario) {this.IngresosSuministrosOperario = IngresosSuministrosOperario;}
    public void setSalidasSuministrosOperario(List<Salida> SalidasSuministrosOperario) {this.SalidasSuministrosOperario = SalidasSuministrosOperario;}
    public void setPasswordKeyOperario(String PasswordKeyOperario) {this.PasswordKeyOperario = PasswordKeyOperario;}
    public void setPasswordOperario(String PasswordOperario) {this.PasswordOperario = PasswordOperario;}    
    
    //	Ingresos - Egresos
    public void addIngresoInsumoOperario(Ingreso IngresoSuministroOperario){
        this.IngresosSuministrosOperario.add(IngresoSuministroOperario);
        if(!IngresoSuministroOperario.getOperarioIngresoSuministro().equals(this)){
            IngresoSuministroOperario.setOperarioIngresoSuministro(this);
        }
    }
    public void removeIngresoSuministroOperario(Ingreso IngresoSuministroOperario){this.IngresosSuministrosOperario.remove(IngresoSuministroOperario);}
    
    public void addSalidaSuministroOperario(Salida SalidaSuministroOperario){
        this.SalidasSuministrosOperario.add(SalidaSuministroOperario);
        if(!SalidaSuministroOperario.getOperarioSalidaSuministro().equals(this)){
            SalidaSuministroOperario.setOperarioSalidaSuministro(this);
        }
    }
    public void removeSalidaSuministroOperario(Salida SalidaSuministroOperario){this.SalidasSuministrosOperario.remove(SalidaSuministroOperario);}
    
    //	Permisos
    public void addPermisoOperario(Permiso PermisoOperario){this.PermisosOperario.add(PermisoOperario);}
    public void removePermisoOperario(Permiso PermisoOperario){this.PermisosOperario.remove(PermisoOperario);}
        
    public List<String> ListarPermisosOperario(){
        List<String> permisos = new ArrayList<>();
        for(Permiso permiso : this.PermisosOperario){
            permisos.add(permiso.getNombrePermiso());
        }
        return permisos;
    }
    
    public boolean TienePermiso(String PermisoOperario){
        for(Permiso permiso : this.PermisosOperario){
            if (permiso.getNombrePermiso().equals(PermisoOperario)) {
                return true;
            }
        }
        return false;
    }    
}