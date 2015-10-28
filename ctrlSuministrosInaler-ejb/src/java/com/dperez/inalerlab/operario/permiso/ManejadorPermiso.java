package com.dperez.inalerlab.operario.permiso;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Named
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManejadorPermiso {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearPermiso(Permiso permiso){
        try{
            em.persist(permiso);
            return permiso.getIdPermiso();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarPermiso(Permiso permiso){
        try{
            em.merge(permiso);
            return permiso.getIdPermiso();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarPermiso(Permiso permiso){
        try{
            em.remove(permiso);
            return permiso.getIdPermiso();
        }catch(Exception ex){}
        return -1;
    }
    
    public Permiso ObtenerPermiso(int IdPermiso){
        TypedQuery<Permiso> query = em.createQuery("FROM Permiso o WHERE o.IdPermiso= :idPermiso", Permiso.class);
        query.setParameter("idPermiso", IdPermiso);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Permiso> ListarPermisos(){
        List<Permiso> Permisos = new ArrayList<>();
        TypedQuery<Permiso> query = em.createQuery("FROM Permiso o", Permiso.class);
        try{
            Permisos = query.getResultList();
        }catch(Exception ex){}
        return Permisos;
    }
}