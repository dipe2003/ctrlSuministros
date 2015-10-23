package com.dperez.inalerlab.suministro.unidad;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Unidad implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdUnidad;
    private String NombreUnidad;
    
    //	Constructores
    public Unidad(){}
    public Unidad(String NombreUnidad){this.NombreUnidad = NombreUnidad;}
    
    //	Getters
    public int getIdUnidad(){return this.IdUnidad;}
    public String getNombreUnidad(){return this.NombreUnidad;}
    
    //	Setters
    public void setIdUnidad(int IdUnidad){this.IdUnidad = IdUnidad;}
    public void setNombreUnidad(String NombreUnidad){this.NombreUnidad = NombreUnidad;}
}