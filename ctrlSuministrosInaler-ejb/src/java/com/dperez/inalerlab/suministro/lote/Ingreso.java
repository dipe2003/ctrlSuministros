package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.operario.Operario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private Date FechaIngreso;
    private float CantidadIngreso;
    private String NumeroFactura;
    @ManyToOne
    private Lote LoteIngreso;
    @ManyToOne
    private Operario OperarioIngresoSuministro;
    private String ObservacionesIngreso;
    
    //	Constructores
    public Ingreso(){}
    public Ingreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, String Observaciones){
        this.FechaIngreso = FechaIngreso;
        this.CantidadIngreso = CantidadIngreso;
        this.NumeroFactura = NumeroFactura;
        this.ObservacionesIngreso = Observaciones;
    }
    
    //	Getters
    public int getIdIngreso(){return this.IdIngreso;}
    public Date getFechaIngreso(){return this.FechaIngreso;}
    public float getCantidadIngreso(){return this.CantidadIngreso;}    
    public Lote getLoteIngreso() {return LoteIngreso;}    
    public Operario getOperarioIngresoSuministro() {return OperarioIngresoSuministro;}
    public String getNumeroFactura() {return NumeroFactura;}
    public String getObservacionesIngreso() {return ObservacionesIngreso;}
    public String getStrFechaIngreso(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(FechaIngreso==null) return "---";
        return sdf.format(FechaIngreso);    
    }
    //	Setters
    public void setIdIngreso(int IdIngreso){this.IdIngreso = IdIngreso;}
    public void setFechaIngreso(Date FechaIngreso){this.FechaIngreso = FechaIngreso;}
    public void setCantidadIngreso(float CantidadIngreso){this.CantidadIngreso = CantidadIngreso;}
    public void setOperarioIngresoSuministro(Operario OperarioIngresoSuministro){        
        if(!OperarioIngresoSuministro.getIngresosSuministrosOperario().contains(this)){
            OperarioIngresoSuministro.addIngresoInsumoOperario(this);
        }
        this.OperarioIngresoSuministro = OperarioIngresoSuministro;
    }
    public void setLoteIngreso(Lote loteIngreso) {        
        if (!loteIngreso.getIngresosLote().contains(this)) {
            loteIngreso.addIngresoLote(this);
        }
        this.LoteIngreso = loteIngreso;
    }
    public void setNumeroFactura(String NumeroFactura) {this.NumeroFactura = NumeroFactura;}
    public void setObservacionesIngreso(String ObservacionesIngreso) {this.ObservacionesIngreso = ObservacionesIngreso;}
    
    
}