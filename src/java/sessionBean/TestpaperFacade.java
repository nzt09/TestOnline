/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Testpaper;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class TestpaperFacade extends AbstractFacade<Testpaper> implements TestpaperFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TestpaperFacade() {
        super(Testpaper.class);
    }

    public List<Testpaper> findByStuId(int stuId) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where stuid=" + stuId + "", Testpaper.class).getResultList();
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
}
