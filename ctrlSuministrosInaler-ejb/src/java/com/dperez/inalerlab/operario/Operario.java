package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.permiso.Permiso;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Salida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Operario implements Serializable {
    
    @Id
    private int IdOperario;
    private String NombreOperario;
    private String ApellidoOperario;
    private String PasswordKeyOperario;
    private String PasswordOperario;
    private String CorreoOperario;
    private boolean RecibeAlertas;
    private boolean EsVigente;
    
    @OneToMany(mappedBy = "OperarioIngresoSuministro",  cascade = CascadeType.ALL)
    private List<Ingreso> IngresosSuministrosOperario;
    @OneToMany(mappedBy = "OperarioSalidaSuministro", cascade = CascadeType.ALL)
    private List<Salida> SalidasSuministrosOperario;
    @ManyToOne
    private Permiso PermisoOperario;
    
    //	Constructores
    public Operario() {
    }
    
    public Operario(int IdOperario, String NombreOperario, String ApellidoOperario, String PasswordKeyOperario, String PasswordOperario, String CorreoOperario) {
        this.IdOperario = IdOperario;
        this.NombreOperario = NombreOperario;
        this.ApellidoOperario = ApellidoOperario;
        this.PasswordKeyOperario = PasswordKeyOperario;
        this.PasswordOperario = PasswordOperario;
        this.CorreoOperario = CorreoOperario;
        this.IngresosSuministrosOperario = new ArrayList<>();
        this.IngresosSuministrosOperario = new ArrayList<>();
        this.RecibeAlertas = false;
        this.EsVigente = true;
    }
    
    //<editor-fold desc="Getters">
    public int getIdOperario() {
        return this.IdOperario;
    }
    
    public String getNombreOperario() {
        return this.NombreOperario;
    }
    
    public String getApellidoOperario() {
        return this.ApellidoOperario;
    }
    
    public Permiso getPermisoOperario() {
        return this.PermisoOperario;
    }
    
    public List<Ingreso> getIngresosSuministrosOperario() {
        return IngresosSuministrosOperario;
    }
    
    public List<Salida> getSalidasSuministrosOperario() {
        return SalidasSuministrosOperario;
    }
    
    public String getPasswordKeyOperario() {
        return PasswordKeyOperario;
    }
    
    public String getPasswordOperario() {
        return PasswordOperario;
    }
    
    public String getCorreoOperario() {
        return CorreoOperario;
    }
    
    public boolean isRecibeAlertas() {
        return RecibeAlertas;
    }
    
    public boolean isEsVigente() {
        return EsVigente;
    }
    
    public String getNombreCompleto(){
        return this.NombreOperario + " " + this.ApellidoOperario;
    }
    //</editor-fold>
    //<editor-fold desc="Setters">
    public void setIdOperario(int IdOperario) {
        this.IdOperario = IdOperario;
    }
    
    public void setNombreOperario(String NombreOperario) {
        this.NombreOperario = NombreOperario;
    }
    
    public void setApellidoOperario(String ApellidoOperario) {
        this.ApellidoOperario = ApellidoOperario;
    }
    
    public void setPermisosOperario(Permiso PermisoOperario) {
        this.PermisoOperario = PermisoOperario;
        if (!PermisoOperario.getOperariosPermiso().contains(this)) {
            PermisoOperario.getOperariosPermiso().add(this);
        }
    }
    
    public void setIngresosSuministrosOperario(List<Ingreso> IngresosSuministrosOperario) {
        this.IngresosSuministrosOperario = IngresosSuministrosOperario;
    }
    
    public void setSalidasSuministrosOperario(List<Salida> SalidasSuministrosOperario) {
        this.SalidasSuministrosOperario = SalidasSuministrosOperario;
    }
    
    public void setPasswordKeyOperario(String PasswordKeyOperario) {
        this.PasswordKeyOperario = PasswordKeyOperario;
    }
    
    public void setPasswordOperario(String PasswordOperario) {
        this.PasswordOperario = PasswordOperario;
    }
    
    public void setCorreoOperario(String CorreoOperario) {
        this.CorreoOperario = CorreoOperario;
    }
    
    public void setPermisoOperario(Permiso PermisoOperario) {
        this.PermisoOperario = PermisoOperario;
    }
    
    public void setRecibeAlertas(boolean RecibeAlertas) {
        this.RecibeAlertas = RecibeAlertas;
    }
    
    public void setEsVigente(boolean EsVigente) {
        this.EsVigente = EsVigente;
    }
    //</editor-fold>
    
    //	Ingresos - Egresos
    public void addIngresoInsumoOperario(Ingreso IngresoSuministroOperario) {
        this.IngresosSuministrosOperario.add(IngresoSuministroOperario);
    }
    
    public void removeIngresoSuministroOperario(Ingreso IngresoSuministroOperario) {
        if(this.IngresosSuministrosOperario.stream()
                .anyMatch((Ingreso ingreso)-> ingreso.getIdIngreso() == IngresoSuministroOperario.getIdIngreso())){
            this.IngresosSuministrosOperario.remove(IngresoSuministroOperario);
        }
    }
    
    public void addSalidaSuministroOperario(Salida SalidaSuministroOperario) {
        this.SalidasSuministrosOperario.add(SalidaSuministroOperario);
    }
    
    public void removeSalidaSuministroOperario(Salida SalidaSuministroOperario) {
        if(this.SalidasSuministrosOperario.stream()
                .anyMatch((Salida salida)->salida.getIdSalida() == SalidaSuministroOperario.getIdSalida())){
            this.SalidasSuministrosOperario.remove(SalidaSuministroOperario);
        }
    }
    
}
