package com.dperez.inalerlab.proveedor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Map;
import javax.persistence.Query;

@Named
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManejadorProveedor {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearProveedor(Proveedor Proveedor){
        try{
            em.persist(Proveedor);
            return Proveedor.getIdProveedor();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarProveedor(Proveedor Proveedor){
        try{
            em.merge(Proveedor);
            return Proveedor.getIdProveedor();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarProveedor(Proveedor Proveedor){
        try{
            em.remove(Proveedor);
            return Proveedor.getIdProveedor();
        }catch(Exception ex){}
        return -1;
    }
    
    public Proveedor ObtenerProveedor(int IdProveedor){
        TypedQuery<Proveedor> query = em.createQuery("FROM Proveedor u WHERE u.IdProveedor= :idProveedor", Proveedor.class);
        query.setParameter("idProveedor", IdProveedor);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Proveedor> ListarProveedores(){
        List<Proveedor> Proveedores = new ArrayList<>();
        TypedQuery<Proveedor> query = em.createQuery("FROM Proveedor p", Proveedor.class);
        try{
            Proveedores = query.getResultList();
        }catch(Exception ex){}
        return Proveedores;
    }
    
    public Map<String, Integer> ListarMapProveedores(){
        Map<String, Integer> map = new HashMap<>();
        Query query = em.createQuery("SELECT p.NombreProveedor, p.IdProveedor FROM Proveedor p");
        try{
            List<Object[]> resultado = query.getResultList();
            for(Object[] res: resultado){
                map.put((String) res[0], (int) res[1]);
            }
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return map;
    }
}