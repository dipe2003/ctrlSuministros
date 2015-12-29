package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.seguridad.ControladorSeguridad;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.operario.permiso.Permiso;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
/**
 * Facade para el Registro, Modificacion y Listado de Operarios.
 * Ofrece los metodos de validación para utilizar en login.
 * @author dperez
 */
@Named
@Stateless
public class FacadeManejoOperario implements Serializable {
    @Inject
    private ControladorOperario cOperario;
    @Inject
    private ControladorPermiso cPermiso;
    @Inject
    private ControladorSeguridad cSeg;

    /**
     * Lista todos los operario registrados en la base de datos.
     * @return Retorna una lista vacia si no existen operarios registrados.
     */
    public List<Operario> ListarOperarios(){
        return cOperario.ListarOperarios();
    }
    
    /**
     * Busca un operario registrado en la base de datos.
     * El operario debe estar registrado previamente.
     * @param IdOperario Numero de Operario
     * @return 
     */
    public Operario BuscarOperario(int IdOperario){
        return cOperario.BuscarOperario(IdOperario); 
    }
    
    /**
     * Lista todos los permisos existentes.
     * @return Retorna una lista de permisos.
     */
    public List<Permiso> ListarStrPermisos(){
        return cPermiso.ListarPermisos();
    }

    /**
     * Comprueba la existencia de un operario registrado en la base de datos según el id especificado.
     * @param IdOperario Numero de Operario
     * @return Retorna <b>True</b> si existe el operario.
     */
    public boolean ExisteOperario(int IdOperario){
        return cOperario.BuscarOperario(IdOperario)!=null;
    }    
    
    /**
     * Comprueba que el password ingresado para el operario sea correcto.
     * El operario debe estar registrado previamente.
     * @param IdOperario Numero de Operario
     * @param Password 
     * @return Retorna <b>True</b> si el password es correcto para el usuario especificado.
     */
    public boolean ValidarOperario(int IdOperario, String Password){
        return cOperario.ValidarOperario(IdOperario, Password);
    }
    
    /**
     * Registra un operario en la base de datos.
     * @param IdOperario
     * @param NombreOperario
     * @param ApellidoOperario
     * @param Password
     * @param CorreoOperario
     * @return Retorna el id del operario creado. Si no se creo retorna -1.
     */
    public int RegistrarOperario(int IdOperario, String NombreOperario, String ApellidoOperario, 
            String Password, String CorreoOperario){        
        return cOperario.CrearOperario(IdOperario, NombreOperario, ApellidoOperario,  Password, CorreoOperario);
    }
    
    /**
     * Agrega el permiso especificado al operario indicado por su id.
     * No se comprueba que el permiso no este ya agregado.
     * @param IdOperario
     * @param IdPermiso
     * @return Retorna el id del operario si se agrego. Retorna -1 si no se pudo agregar.
     */
    public int AgregarPermiso(int IdOperario, int IdPermiso){
        return cOperario.AgregarPermiso(IdOperario, cPermiso.BuscarPermiso(IdPermiso));
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
        return cOperario.ModificarDatosOperario(IdOperario, NombreOperario, ApellidoOperario, CorreoOperario, RecibeAlertas);
    }
    
    /**
     * Modifica el password del usuario
     * PRE: Se realizaron los controles pertinentes de password actual.
     * @param IdOperario
     * @param PasswordNuevo Password que se asociara al operario.
     * @return Retorna el id del operario se se actualizo. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarPassword(int IdOperario, String PasswordNuevo){
        return cOperario.ModificarPassword(IdOperario,PasswordNuevo);
    }
    
    /**
     * Devuelve los nombres de los operarios registrados
     * @return Retorna un Map con los nombres completos de los operarios (key) y sus id (value).
     */
    public Map<String, Integer> GetNombresOperarios(){
        return cOperario.GetMapNombresOperarios();
    }
}