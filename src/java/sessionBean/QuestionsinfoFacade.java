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

    public List<Questionsinfo> findByCourseId(String sql) {
        List<Questionsinfo> tem = em.createNativeQuery(sql, Questionsinfo.class).getResultList();
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }

    @Override
    public List executQuery(String sqlString) {
        return em.createNativeQuery(sqlString).getResultList();
    }

    @Override
    public int executQuery2(String sqlString) {
        System.out.println(em.createNativeQuery(sqlString).getResultList().size() + "iiiiiiiiiiiiiiiiiiiii");
        return em.createNativeQuery(sqlString).getResultList().size();
    }
}
