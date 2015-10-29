package com.dperez.inalerlab.suministro.stockminimo;

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
public class ManejadorStockMinimo {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearStockMinimo(StockMinimo StockMinimo){
        try{
            em.persist(StockMinimo);
            return StockMinimo.getIdStockMinimo();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarStockMinimo(StockMinimo StockMinimo){
        try{
            em.merge(StockMinimo);
            return StockMinimo.getIdStockMinimo();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarStockMinimo(StockMinimo StockMinimo){
        try{
            em.remove(StockMinimo);
            return StockMinimo.getIdStockMinimo();
        }catch(Exception ex){}
        return -1;
    }
    
    public StockMinimo ObtenerStockMinimo(int IdStockMinimo){
        TypedQuery<StockMinimo> query = em.createQuery("FROM StockMinimo s WHERE s.IdStockMinimo= :idStockMinimo", StockMinimo.class);
        query.setParameter("idStockMinimo", IdStockMinimo);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
}