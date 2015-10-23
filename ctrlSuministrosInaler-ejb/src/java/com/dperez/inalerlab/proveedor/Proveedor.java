package com.dperez.inalerlab.proveedor;

import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Proveedor implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdProveedor;
    private String NombreProveedor;
    private String ContactoProveedor;
    @OneToMany(mappedBy = "ProveedorSuministro")
    private List<Suministro> SuministrosProveedor;
    
    //	Constructores
    public Proveedor() {}
    public Proveedor(String NombreProveedor, String ContactoProveedor){
        this.NombreProveedor = NombreProveedor;
        this.ContactoProveedor = ContactoProveedor;
        this.SuministrosProveedor = new ArrayList<>();
    }
    
    //	Getters
    public int getIdProveedor(){return this.IdProveedor;}
    public String getNombreProveedor(){return this.NombreProveedor;}
    public String getContactoProveedor(){return this.ContactoProveedor;}
    public List<Suministro> getSuministrosProveedor(){return this.SuministrosProveedor;}
    
    //	Setters
    public void setIdProveedor(int IdProveedor){this.IdProveedor = IdProveedor;}
    public void setNombreProveedor(String NombreProveedor){this.NombreProveedor = NombreProveedor;}
    public void setContactoProveedor(String ContactoProveedor){this.ContactoProveedor = ContactoProveedor;}
    public void setSuministrosProveedor(List<Suministro> SuministrosProveedor){this.SuministrosProveedor = SuministrosProveedor;}
    
    //	SuministrosProveedor
    public void addSuministroProveedor(Suministro SuministroProveedor){this.SuministrosProveedor.add(SuministroProveedor);}
    public void removeSuministroProveedor(Suministro SuministroProveedor){this.SuministrosProveedor.remove(SuministroProveedor);}
}