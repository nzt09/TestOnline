/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Departclass;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
     public List<Departclass> findConstrainRange( int[] range,int departmentId) {
        List<Departclass> tem = em.createNativeQuery("select * from departclass where department= "+ departmentId , Departclass.class).getResultList();
//        System.out.println("select * from teacher where roleid=" + roleId + " and departmentid=" + departmentId);
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }
}
