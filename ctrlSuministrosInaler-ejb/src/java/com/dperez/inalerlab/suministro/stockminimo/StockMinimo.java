package com.dperez.inalerlab.suministro.stockminimo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class StockMinimo implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdStockMinimo;
    private float CantidadStockMinimo;
    @Temporal(TemporalType.DATE)
    private Date FechaVigenteStockMinimo;
    private boolean VigenciaStockMinimo;
    
    //	Constructores
    public StockMinimo(){}
    public StockMinimo(float CantidadStockMinimo, Date FechaVigenteStockMinimo, boolean VigenciaStockMinimo){
        this.CantidadStockMinimo = CantidadStockMinimo;
        this.FechaVigenteStockMinimo = FechaVigenteStockMinimo;
        this.VigenciaStockMinimo = VigenciaStockMinimo;
    }
    
    //	Getters
    public int getIdStockMinimo(){return this.IdStockMinimo;}
    public float getCantidadStockMinimo(){return this.CantidadStockMinimo;}
    public Date getFechaVigenteStockMinimo(){return this.FechaVigenteStockMinimo;}
    public boolean getVigenciaStockMinimo(){return this.VigenciaStockMinimo;}
    public String getStrFechaVigenteStockMinimo(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(FechaVigenteStockMinimo==null) return "---";
        return sdf.format(FechaVigenteStockMinimo);  
    }
    //	Setters
    public void setIdStockMinimo(int IdStockMinimo){this.IdStockMinimo = IdStockMinimo;}
    public void setCantidadStockMinimo(float CantidadStockMinimo){this.CantidadStockMinimo = CantidadStockMinimo;}
    public void setFechaVigenteStockMinimo(Date FechaVigenteStockMinimo){this.FechaVigenteStockMinimo = FechaVigenteStockMinimo;}
    public void setVigente(boolean VigenciaStockMinimo){this.VigenciaStockMinimo = VigenciaStockMinimo;}
}