package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.permiso.EnumPermiso;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorOperario implements Serializable{
    @Inject
    private ManejadorOperario mOperario;
    
    /**
     * Crea un operario en la base de datos.
     * @param IdOperario
     * @param NombreOperario
     * @param ApellidoOperario
     * @param Password
     * @return 
     */
    public Operario CrearOperario(int IdOperario, String NombreOperario, String ApellidoOperario, String Password){
        Operario operario = new Operario(IdOperario, NombreOperario, ApellidoOperario, Password);
        if(mOperario.CrearOperario(operario)!=-1){
            return operario;
        }
        return null;
    }
    
    /**
     * Busca un operario en la base de datos.
     * @param IdOperario
     * @return 
     */
    public Operario BuscarOperario(int IdOperario){
        return mOperario.ObtenerOperario(IdOperario);
    }
    
    /**
     * Devuelve una lista con todos los operarios registrados en la base de datos.
     * Si no hay operarios registrados devuelve una lista vacia.
     * @return 
     */
    public List<Operario> ListarOperarios(){
        return mOperario.ListarOperarios();
    }
    
    /**
     * Comprueba si el password para el operario especificado es correcto.
     * El operario debe existir en la base de datos.
     * @param IdOperario
     * @param Password
     * @return retorna <b>True</b> si es valido.
     */
    public boolean ValidarOperario(int IdOperario, String Password){
        Operario operario = mOperario.ObtenerOperario(IdOperario);
        return operario.EsPasswordValido(Password);
    }
    
    /**
     * Agrega el permiso indicado al usuario especificado por su id.
     * @param IdOperario
     * @param Permiso
     * @return retorna el id del operario si se agergo, sino retorna -1.
     */
    public int AgregarPermiso(int IdOperario, EnumPermiso Permiso){
        Operario operario = mOperario.ObtenerOperario(IdOperario);
        if (operario!=null) {
            operario.addPermisoOperario(Permiso);
            return mOperario.ActualizarOperario(operario);
        }
        return -1;
    }
    
    /**
     * Actualiza los datos de un usuario especificado por su id.
     * @param IdOperario
     * @param NombreOperario
     * @param ApellidoOperario
     * @return Retorna el id del operario. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarDatosOperario(int IdOperario, String NombreOperario, String ApellidoOperario){
        Operario operario = mOperario.ObtenerOperario(IdOperario);
        operario.setNombreOperario(NombreOperario);
        operario.setApellidoOperario(ApellidoOperario);
        return mOperario.ActualizarOperario(operario);
    }
    
    /**
     * Modifica el password del usuario si PasswordActual coincide con el password del operario.
     * @param IdOperario
     * @param PasswordActual Password asociado al operario en la base de datos.
     * @param PasswordNuevo Password que se asociara al operario.
     * @return Retorna el id del operario se se actualizo. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarPassword(int IdOperario, String PasswordActual, String PasswordNuevo){
        try{
            Operario operario = mOperario.ObtenerOperario(IdOperario);
            operario.setPassword(PasswordActual, PasswordNuevo);
            return mOperario.ActualizarOperario(operario);
        }catch(IllegalArgumentException ex){}
        return -1;
    }
}	