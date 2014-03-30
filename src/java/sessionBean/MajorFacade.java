/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Major;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class MajorFacade extends AbstractFacade<Major> implements MajorFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MajorFacade() {
        super(Major.class);
    }

    public List<Major> findByDepartmentId(int department) {
        List<Major> tem = em.createNativeQuery("select * from major where department=" + department + "", Major.class).getResultList();
        return tem;
    }
}
