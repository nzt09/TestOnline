/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Knowledge;
import entities.Questionknowledge;
import entities.Questionknowledge_;
import entities.Questiontypeinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;

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

    public List<Questionknowledge> findRange(int typeid, int knowid, int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(Questionknowledge.class);
        Root<Questionknowledge> questionknowledge = cq.from(Questionknowledge.class);
        Questiontypeinfo qt = new Questiontypeinfo();
        qt.setId(typeid);
        Knowledge kl = new Knowledge();
        kl.setId(knowid);
        cq.where(questionknowledge.get(Questionknowledge_.questiontype).in(qt), questionknowledge.get(Questionknowledge_.knowid).in(kl));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count(int typeid, int knowid) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(Questionknowledge.class);
        Root<Questionknowledge> questionknowledge = cq.from(Questionknowledge.class);
        Questiontypeinfo qt = new Questiontypeinfo();
        qt.setId(typeid);
        Knowledge kl = new Knowledge();
        kl.setId(knowid);
        cq.where(questionknowledge.get(Questionknowledge_.questiontype).in(qt), questionknowledge.get(Questionknowledge_.knowid).in(kl));
        cq.select(getEntityManager().getCriteriaBuilder().count(questionknowledge));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
