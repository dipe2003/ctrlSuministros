package com.dperez.inalerlab.email;

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
public class ManejadorPropiedad {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public String CrearPropiedad(Propiedad Propiedad){
        try{
            em.persist(Propiedad);
            return Propiedad.getNombrePropiedad();
        }catch(Exception ex){}
        return "na";
    }
    
    public String ActualizarPropiedad(Propiedad Propiedad){
        try{
            em.merge(Propiedad);
            return Propiedad.getNombrePropiedad();
        }catch(Exception ex){}
        return "na";
    }
    
    public String BorrarPropiedad(Propiedad Propiedad){
        try{
            em.remove(Propiedad);
            return Propiedad.getNombrePropiedad();
        }catch(Exception ex){}
        return "na";
    }
    
    public Propiedad ObtenerPropiedad(String NombrePropiedad){

        try{
            return em.find(Propiedad.class, NombrePropiedad);            
        }catch(Exception ex){}
        return null;
    }
    
    public List<Propiedad> ListarPropiedades(){
        List<Propiedad> Propiedades = new ArrayList<>();
        TypedQuery<Propiedad> query = em.createQuery("FROM Propiedad o", Propiedad.class);
        try{
            Propiedades = query.getResultList();
        }catch(Exception ex){}
        return Propiedades;
    }
}