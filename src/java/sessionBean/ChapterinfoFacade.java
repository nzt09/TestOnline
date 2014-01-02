/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Chapterinfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class ChapterinfoFacade extends AbstractFacade<Chapterinfo> implements ChapterinfoFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChapterinfoFacade() {
        super(Chapterinfo.class);
    }

    public List<Chapterinfo> findByCourseId(int id) {
        List<Chapterinfo> tem = em.createNativeQuery("select * from chapterinfo where course =" + id + " order by chapternum", Chapterinfo.class).getResultList();
        if (null == tem || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
}
