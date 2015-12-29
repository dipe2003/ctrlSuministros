
package com.dperez.inalerlab.email;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
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
}

