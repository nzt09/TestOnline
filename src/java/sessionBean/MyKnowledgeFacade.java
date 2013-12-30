/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Knowledge;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class MyKnowledgeFacade extends AbstractFacade<Knowledge> implements MyKnowledgeFacadeLocal {
    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyKnowledgeFacade() {
        super(Knowledge.class);
    }
    public List<Knowledge> getKnowledgeList(String sql){
        return em.createNativeQuery(sql, Knowledge.class).getResultList();
    }
    public List<Knowledge> findByChapterId(int id) {
        List<Knowledge> tem = em.createNativeQuery("select * from knowledge where chapter =" + id + "", Knowledge.class).getResultList();
        if (null == tem || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
}
