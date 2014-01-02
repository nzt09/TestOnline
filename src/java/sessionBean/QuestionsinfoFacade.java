/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Questionsinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class QuestionsinfoFacade extends AbstractFacade<Questionsinfo> implements QuestionsinfoFacadeLocal {
    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
        protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionsinfoFacade() {
        super(Questionsinfo.class);
    }

   public List<Questionsinfo> findConstrainRange(int typeid,int knowid, int[] range) {
        List<Questionsinfo> tem = em.createNativeQuery("select * from questionsinfo where questiontype=" + typeid+"and knowid="+knowid, Questionsinfo.class).getResultList();
        System.out.println("select * from questionsinfo where questiontype=" + typeid+"and knowid="+knowid);
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }
}
