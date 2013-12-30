/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Courseinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class CourseinfoFacade extends AbstractFacade<Courseinfo> implements CourseinfoFacadeLocal {
    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseinfoFacade() {
        super(Courseinfo.class);
    }
    public List<Courseinfo> findByCourseId(int id) {
        List<Courseinfo> tem = em.createNativeQuery("select * from courseinfo where id=" + id + "", Courseinfo.class).getResultList();
        return tem;     
    }  
}
