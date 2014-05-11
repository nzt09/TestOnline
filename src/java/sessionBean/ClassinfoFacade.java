/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Classinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class ClassinfoFacade extends AbstractFacade<Classinfo> implements ClassinfoFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClassinfoFacade() {
        super(Classinfo.class);
    }

    public List<Classinfo> findByMajor(int id) {
        List<Classinfo> tem = em.createNativeQuery("select * from classinfo where major=" + id + "", Classinfo.class).getResultList();
        return tem;
    }

    public List<Classinfo> findById(int classId) {
        List<Classinfo> tem = em.createNativeQuery("select * from classinfo where id=" + classId + "", Classinfo.class).getResultList();
        return tem;
    }
    
    public List<Classinfo> findByName(String className) {
        List<Classinfo> tem = em.createNativeQuery("select * from classinfo where classname='" + className + "'", Classinfo.class).getResultList();
        return tem;
    }
}
