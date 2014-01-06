/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Studentinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class StudentinfoFacade extends AbstractFacade<Studentinfo> implements StudentinfoFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentinfoFacade() {
        super(Studentinfo.class);
    }

    public Studentinfo findByIdPassword(String userId, String password) {
        List<Studentinfo> tem = em.createNativeQuery("select * from studentinfo where  stuno='" + userId + "'and password='" + password + "'", Studentinfo.class).getResultList();
        if (null == tem || tem.isEmpty()) {
            return new Studentinfo();
        } else {
            return (Studentinfo) tem.get(0);
        }
    }

    public List<Studentinfo> findById(int id) {
        List<Studentinfo> tem = em.createNativeQuery("select * from studentinfo where  id='" + id + "'", Studentinfo.class).getResultList();
        if (null == tem || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
      
}
