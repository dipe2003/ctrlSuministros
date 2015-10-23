package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.suministro.lote.Lote;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ingreso implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdIngreso;
    @Temporal(TemporalType.DATE)
    private Calendar FechaIngreso;
    private float CantidadIngreso;
    @ManyToOne
    private Lote LoteIngreso;
    @ManyToOne
    private Operario OperarioIngresoSuministro;
    
    //	Constructores
    public Ingreso(){}
    public Ingreso(Calendar FechaIngreso, float CantidadIngreso){
        this.FechaIngreso = FechaIngreso;
        this.CantidadIngreso = CantidadIngreso;
    }
    
    //	Getters
    public int getIdIngreso(){return this.IdIngreso;}
    public Calendar getFechaIngreso(){return this.FechaIngreso;}
    public float getCantidadIngreso(){return this.CantidadIngreso;}    
    public Lote getLoteIngreso() {return LoteIngreso;}    
    public Operario getOperarioIngresoSuministro() {return OperarioIngresoSuministro;}
    
    //	Setters
    public void setIdIngreso(int IdIngreso){this.IdIngreso = IdIngreso;}
    public void setFechaIngreso(Calendar FechaIngreso){this.FechaIngreso = FechaIngreso;}
    public void setCantidadIngreso(float CantidadIngreso){this.CantidadIngreso = CantidadIngreso;}
    public void setOperarioIngresoSuministro(Operario OperarioIngresoSuministro){
        this.OperarioIngresoSuministro = OperarioIngresoSuministro;
        if(!OperarioIngresoSuministro.getIngresosSuministrosOperario().contains(this)){
            OperarioIngresoSuministro.getIngresosSuministrosOperario().add(this);
        }
    }
    public void setLoteIngreso(Lote LoteIngreso) {this.LoteIngreso = LoteIngreso;}
    
}