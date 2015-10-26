package com.dperez.inalerlab.operario.credenciales;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorCredenciales implements Serializable{
    @Inject
    private ManejadorCredenciales mCredenciales;
    @Inject
    private Seguridad moduloSeguridad;
    /**
     * Crea credenciales en la base de datos.
     * @param Password
     * @return 
     */
    public Credenciales CrearCredenciales(String Password){
        String[] seguridad = moduloSeguridad.getPasswordSeguro(Password);
        Credenciales credenciales = new Credenciales(seguridad[1], seguridad[0]);
        if(mCredenciales.CrearCredenciales(credenciales)!=-1){
            return credenciales;
        }
        return null;
    }
    
    /**
     * Busca credenciales en la base de datos.
     * @param IdCredenciales
     * @return 
     */
    public Credenciales BuscarCredenciales(int IdCredenciales){
        return mCredenciales.ObtenerCredenciales(IdCredenciales);
    }
    
    /**
     * Devuelve una lista con todos las credenciales registradas en la base de datos.
     * Si no hay credenciales registradas devuelve una lista vacia.
     * @return 
     */
    public List<Credenciales> ListarCredenciales(){
        return mCredenciales.ListarCredenciales();
    }
    
    /**
     * Compara el password guardado con el ingresado.
     * @param Password
     * @param IdCredenciales
     * @return 
     */
    public boolean ValidarPassword(String Password, int IdCredenciales){
        Credenciales credenciales = mCredenciales.ObtenerCredenciales(IdCredenciales);
        if (credenciales.getPasswordCredenciales().equals(moduloSeguridad.getPasswordSeguro(Password, credenciales.getSaltCredenciales()))) {
            return true;
        }
        return false;
    }
    
}	