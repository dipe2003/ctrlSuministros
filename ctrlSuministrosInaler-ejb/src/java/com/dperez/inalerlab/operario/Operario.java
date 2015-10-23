package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.permiso.EnumPermiso;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Salida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Operario implements Serializable{
    @Id
    private int IdOperario;
    private String NombreOperario;
    private String ApellidoOperario;
    private String Password;
    @OneToMany(mappedBy = "OperarioIngresoSuministro" )
    private List<Ingreso> IngresosSuministrosOperario;
    @OneToMany(mappedBy = "OperarioSalidaSuministro")
    private List<Salida> SalidasSuministrosOperario;
    @OneToMany
    private Map<String, EnumPermiso> PermisosOperario;
    
    //	Constructores
    public Operario(){}
    public Operario(int IdOperario, String NombreOperario, String ApellidoOperario, String Password){
        this.IdOperario = IdOperario;
        this.NombreOperario = NombreOperario;
        this.ApellidoOperario = ApellidoOperario;
        this.Password = Password;
        this.IngresosSuministrosOperario = new ArrayList<>();
        this.IngresosSuministrosOperario = new ArrayList<>();
        this.PermisosOperario = new HashMap<>();
    }
    
    //	Getters
    public int getIdOperario(){return this.IdOperario;}
    public String getNombreOperario(){return this.NombreOperario;}
    public String getApellidoOperario(){return this.ApellidoOperario;}
    public Map<String, EnumPermiso> getPermisosOperario(){return this.PermisosOperario;}    
    public List<Ingreso> getIngresosSuministrosOperario() {return IngresosSuministrosOperario;}    
    public List<Salida> getSalidasSuministrosOperario() {return SalidasSuministrosOperario;}
    
    //	Setters
    public void setIdOperario(int IdOperario){this.IdOperario = IdOperario;}
    public void setNombreOperario(String NombreOperario){this.NombreOperario = NombreOperario;}
    public void setApellidoOperario(String ApellidoOperario){this.ApellidoOperario = ApellidoOperario;}
    public void setPermisosOperario(Map<String, EnumPermiso> PermisosOperario){this.PermisosOperario = PermisosOperario;}
    public void setPassword(String PasswordActual, String PasswordNuevo)throws IllegalArgumentException{
        if(!this.Password.equals(PasswordActual)) throw new IllegalArgumentException("El password no es correcto.");
        this.Password = PasswordNuevo;
    }
    public void setIngresosSuministrosOperario(List<Ingreso> IngresosSuministrosOperario) {this.IngresosSuministrosOperario = IngresosSuministrosOperario;}
    public void setSalidasSuministrosOperario(List<Salida> SalidasSuministrosOperario) {this.SalidasSuministrosOperario = SalidasSuministrosOperario;}
    
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
    public void addPermisoOperario(EnumPermiso PermisoOperario){this.PermisosOperario.put(PermisoOperario.toString(), PermisoOperario);}
    public void removePermisoOperario(String PermisoOperario){this.PermisosOperario.remove(PermisoOperario);}
    
    public List<String> ListarPermisosOperario(){
        List<String> permisos = new ArrayList<>();
        for(String key : this.PermisosOperario.keySet()){
            permisos.add(this.PermisosOperario.get(key).toString());
        }
        return permisos;
    }
    
    public boolean TienePermiso(String PermisoOperario){
        return this.PermisosOperario.get(PermisoOperario)!=null;
    }
    
    public boolean EsPasswordValido(String Password){
        return this.Password.equals(Password);
    }
    
}