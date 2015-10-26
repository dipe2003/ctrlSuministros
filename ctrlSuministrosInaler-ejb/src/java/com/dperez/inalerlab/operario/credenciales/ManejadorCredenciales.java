package com.dperez.inalerlab.operario.credenciales;

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
public class ManejadorCredenciales {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearCredenciales(Credenciales credenciales){
        try{
            em.persist(credenciales);
            return credenciales.getIdCredenciales();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarCredenciales(Credenciales credenciales){
        try{
            em.merge(credenciales);
            return credenciales.getIdCredenciales();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarCredenciales(Credenciales credenciales){
        try{
            em.remove(credenciales);
            return credenciales.getIdCredenciales();
        }catch(Exception ex){}
        return -1;
    }
    
    public Credenciales ObtenerCredenciales(int IdCredenciales){
        TypedQuery<Credenciales> query = em.createQuery("SELECT o FROM Credenciales o WHERE o.IdCredenciales= :idCredenciales", Credenciales.class);
        query.setParameter("idCredenciales", IdCredenciales);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Credenciales> ListarCredenciales(){
        List<Credenciales> Credenciales = new ArrayList<>();
        TypedQuery<Credenciales> query = em.createQuery("FROM Credenciales o", Credenciales.class);
        try{
            Credenciales = query.getResultList();
        }catch(Exception ex){}
        return Credenciales;
    }
}