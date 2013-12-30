/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Classquestiondifficult;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class ClassquestiondifficultFacade extends AbstractFacade<Classquestiondifficult> implements ClassquestiondifficultFacadeLocal {
    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClassquestiondifficultFacade() {
        super(Classquestiondifficult.class);
    }
    
}
