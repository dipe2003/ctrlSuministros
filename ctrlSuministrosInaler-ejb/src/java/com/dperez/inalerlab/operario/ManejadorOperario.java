package com.dperez.inalerlab.operario;

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
public class ManejadorOperario {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearOperario(Operario Operario){
        try{
            em.persist(Operario);
            return Operario.getIdOperario();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarOperario(Operario Operario){
        try{
            em.merge(Operario);
            return Operario.getIdOperario();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarOperario(Operario Operario){
        try{
            em.remove(Operario);
            return Operario.getIdOperario();
        }catch(Exception ex){}
        return -1;
    }
    
    public Operario ObtenerOperario(int IdOperario){
        TypedQuery<Operario> query = em.createQuery("FROM Operario o WHERE o.IdOperario= :idOperario", Operario.class);
        query.setParameter("idOperario", IdOperario);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Operario> ListarOperarios(){
        List<Operario> Operarios = new ArrayList<>();
        TypedQuery<Operario> query = em.createQuery("FROM Operario o", Operario.class);
        try{
            Operarios = query.getResultList();
        }catch(Exception ex){}
        return Operarios;
    }
}