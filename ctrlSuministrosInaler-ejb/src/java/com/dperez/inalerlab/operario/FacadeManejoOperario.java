package com.dperez.inalerlab.operario;

import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.operario.permiso.Permiso;
import java.io.Serializable;
import java.util.List;
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
     * @return Retorna el operario creado.
     */
    public Operario RegistrarOperario(int IdOperario, String NombreOperario, String ApellidoOperario, 
            String Password){
        return cOperario.CrearOperario(IdOperario, NombreOperario, ApellidoOperario, Password);
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
     * @return Retorna el id del operario. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarDatosOperario(int IdOperario, String NombreOperario, String ApellidoOperario){
        return cOperario.ModificarDatosOperario(IdOperario, NombreOperario, ApellidoOperario);
    }
    
    /**
     * Modifica el password del usuario si PasswordActual coincide con el password del operario.
     * @param IdOperario
     * @param PasswordActual Password asociado al operario en la base de datos.
     * @param PasswordNuevo Password que se asociara al operario.
     * @return Retorna el id del operario se se actualizo. Retorna -1 si no se pudo actualizar.
     */
//    public int ModificarPassword(int IdOperario, String PasswordActual, String PasswordNuevo){
//        return cOperario.ModificarPassword(IdOperario, PasswordActual, PasswordNuevo);
//    }
}