/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Question2knowledge;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class Question2knowledgeFacade extends AbstractFacade<Question2knowledge> implements Question2knowledgeFacadeLocal {
    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Question2knowledgeFacade() {
        super(Question2knowledge.class);
    }
    
    public Question2knowledge findByQusetionId(int id){
        List<Question2knowledge> tem=em.createNativeQuery("select *  from Question2knowledge where questionid="+id,Question2knowledge.class).getResultList();
        return tem.get(0);
    }
    
    public Question2knowledge findLast(){
        List<Question2knowledge> tem=em.createNativeQuery("select *  from Question2knowledge order by id",Question2knowledge.class).getResultList();
        return tem.get(tem.size()-1);
    }
}
