/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Questionknowledge;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class QuestionknowledgeFacade extends AbstractFacade<Questionknowledge> implements QuestionknowledgeFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionknowledgeFacade() {
        super(Questionknowledge.class);
    }

    public List<Questionknowledge> findRange(int typeid, int knowid) {
        List<Questionknowledge> tem = em.createNativeQuery("select * from questionknowledge where questiontype=" + typeid + "and knowid=" + knowid, Questionknowledge.class).getResultList();
        System.out.println("select * from questionknowledge where questiontype=" + typeid + "and knowid=" + knowid);
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }
    
    public Questionknowledge findById(int id){
        List<Questionknowledge> tem=em.createNativeQuery("select * from questionknowledge where id="+id, Questionknowledge.class).getResultList();
        return tem.get(0);
    }
}
