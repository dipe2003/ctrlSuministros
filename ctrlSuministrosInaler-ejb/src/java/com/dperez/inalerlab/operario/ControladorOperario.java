package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.seguridad.ControladorSeguridad;
import com.dperez.inalerlab.operario.permiso.Permiso;
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
    @Inject
    private ControladorSeguridad cSeg;
    /**
     * Crea un operario en la base de datos.
     * @param IdOperario
     * @param NombreOperario
     * @param ApellidoOperario
     * @param PasswordOperario
     * @param CorreoOperario
     * @return Retorna el id del operario creado. Si no se creo retorna -1.
     */
    public int CrearOperario(int IdOperario, String NombreOperario, String ApellidoOperario, String PasswordOperario, String CorreoOperario){
        String[] seg = cSeg.getPasswordSeguro(PasswordOperario);
        Operario operario = new Operario(IdOperario, NombreOperario, ApellidoOperario, seg[0], seg[1], CorreoOperario);        
        return mOperario.CrearOperario(operario);
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
        try{
            Operario operario = mOperario.ObtenerOperario(IdOperario);
            if (!operario.isEsVigente()){
                return false;
            }
            String seg = cSeg.getPasswordSeguro(Password, operario.getPasswordKeyOperario());
            return operario.getPasswordOperario().equals(seg);
        }catch(NullPointerException ex){}
        return false;
    }
    
    /**
     * Agrega el permiso indicado al usuario especificado por su id.
     * @param IdOperario
     * @param PermisoOperario
     * @return retorna el id del operario si se agergo, sino retorna -1.
     */
    public int AgregarPermiso(int IdOperario, Permiso PermisoOperario){
        Operario operario = mOperario.ObtenerOperario(IdOperario);
        try{
            operario.setPermisosOperario(PermisoOperario);
            return mOperario.ActualizarOperario(operario);
        }catch(NullPointerException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return -1;
    }
    
    /**
     * Actualiza los datos de un usuario especificado por su id.
     * @param IdOperario
     * @param NombreOperario
     * @param ApellidoOperario
     * @param CorreoOperario
     * @param RecibeAlertas
     * @return Retorna el id del operario. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarDatosOperario(int IdOperario, String NombreOperario, String ApellidoOperario, String CorreoOperario, boolean RecibeAlertas){
        Operario operario = mOperario.ObtenerOperario(IdOperario);
        operario.setNombreOperario(NombreOperario);
        operario.setApellidoOperario(ApellidoOperario);
        operario.setCorreoOperario(CorreoOperario);
        operario.setRecibeAlertas(RecibeAlertas);
        return mOperario.ActualizarOperario(operario);
    }
    
    /**
     * Modifica el password del usuario.
     * @param IdOperario
     * @param PasswordNuevo Password que se asociara al operario.
     * @return Retorna el id del operario se se actualizo. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarPassword(int IdOperario, String PasswordNuevo){
        try{
            Operario operario = mOperario.ObtenerOperario(IdOperario);
            String[] pass = cSeg.getPasswordSeguro(PasswordNuevo);
            operario.setPasswordOperario(pass[1]);
            operario.setPasswordKeyOperario(pass[0]);
            return mOperario.ActualizarOperario(operario);
        }catch(IllegalArgumentException ex){}
        return -1;
    }

    public int CambiarVigenciaOperario(int idOperario, boolean esVigente){
        try{
            Operario operario = mOperario.ObtenerOperario(idOperario);
            operario.setEsVigente(esVigente);
            return mOperario.ActualizarOperario(operario);
        }catch(IllegalArgumentException ex){}
        return -1;
    }
}	