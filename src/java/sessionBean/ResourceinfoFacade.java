/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Resourceinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class ResourceinfoFacade extends AbstractFacade<Resourceinfo> implements ResourceinfoFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResourceinfoFacade() {
        super(Resourceinfo.class);
    }

    public List<Resourceinfo> findByResourceinfo(String sql) {
        List<Resourceinfo> tem = em.createNativeQuery(sql, Resourceinfo.class).getResultList();
        if (null == tem || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }

    }

}
