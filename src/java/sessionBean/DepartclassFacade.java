/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Departclass;
import entities.Departclass_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrator
 */
@Stateless
public class DepartclassFacade extends AbstractFacade<Departclass> implements DepartclassFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartclassFacade() {
        super(Departclass.class);
    }

    public List<Departclass> findRange( int[] range,int departmentId,int classId,int majorId) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(Departclass.class);
        Root<Departclass> departclass = cq.from(Departclass.class);
        if(classId==0){
             if(majorId==0){
                 cq.where(departclass.get(Departclass_.department).in(departmentId));
             }  
             else{
                 cq.where(departclass.get(Departclass_.majorid).in(majorId));
             }
        }
        else{ 
            cq.where(departclass.get(Departclass_.classid).in(classId));
        } 
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    public int count(int classId,int departmentId,int majorId) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(Departclass.class);
        Root<Departclass> departclass = cq.from(Departclass.class);
        if(classId==0){
             if(majorId==0){
                 cq.where(departclass.get(Departclass_.department).in(departmentId));
             }  
             else{
                 cq.where(departclass.get(Departclass_.majorid).in(majorId));
             }
        }
        else{ 
            cq.where(departclass.get(Departclass_.classid).in(classId));
        } 
        cq.select(getEntityManager().getCriteriaBuilder().count(departclass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
