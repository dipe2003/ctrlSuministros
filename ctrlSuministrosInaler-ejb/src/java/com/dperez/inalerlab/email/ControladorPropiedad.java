
package com.dperez.inalerlab.email;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
@ManagedBean
public class ControladorPropiedad {
    @Inject
    private ManejadorPropiedad mProp;
    
    public ControladorPropiedad(){}       
    
    public String getMailFrom(){
        return mProp.ObtenerPropiedad("mail_from").getValorPropiedad();
    }
    public String getMailSmtp(){
        return mProp.ObtenerPropiedad("mail_smtp").getValorPropiedad();
    }
    public String getMailUser(){
        return mProp.ObtenerPropiedad("mail_user").getValorPropiedad();
    }
    public String getMailPass(){
        return mProp.ObtenerPropiedad("mail_pass").getValorPropiedad();
    }
    public int getMailPort(){
        return Integer.parseInt(mProp.ObtenerPropiedad("mail_port").getValorPropiedad());
    }
    
    public boolean getUseTtls(){
        try{
            return Boolean.getBoolean(mProp.ObtenerPropiedad("usar_ttls").getValorPropiedad());
        }catch(Exception ex){}
        return false;
    }
    
    //  Edicion de propiedades
    public List<Propiedad> getPropiedades(){
        return mProp.ListarPropiedades();
    }
    
    public boolean guardarPropiedad(String nombrePropiedad, String valorPropiedad){
        Propiedad prop = mProp.ObtenerPropiedad(nombrePropiedad);
        try{
            prop.setValorPropiedad(valorPropiedad);
        }catch(NullPointerException ex){}
        return !mProp.ActualizarPropiedad(prop).equals("na"); 
    }
    
}

