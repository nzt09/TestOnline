/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Testpaper;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class TestpaperFacade extends AbstractFacade<Testpaper> implements TestpaperFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TestpaperFacade() {
        super(Testpaper.class);
    }

    public List<Testpaper> findByStuId(int stuId) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where stuid=" + stuId + "", Testpaper.class).getResultList();
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
    
    public List<Testpaper> findByStuIdFormal(int stuId) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where stuid=" + stuId + " and testassignid is not null", Testpaper.class).getResultList();
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }

    public List<Testpaper> findByCourseBystuid(int courseId, int classId) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where course=" + courseId + " and stuid in(select id from studentinfo where classid=" + classId + ") and testassignid is not null order by score ", Testpaper.class).getResultList();
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }

    public List<Testpaper> findByTestAssignId(int testAssignid) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where testassignid=" + testAssignid, Testpaper.class).getResultList();
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
    
    public List<Testpaper> findByCourseByClassID(int courseId, int classId) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where course=" + courseId + " and stuid in(select id from studentinfo where classid=" + classId + ")and testassignid is not null order by score  ", Testpaper.class).getResultList();
        System.out.println("select * from testpaper where course=" + courseId + "and stuid in(select id from studentinfo where classid=" + classId + ")");
        System.out.println(tem.size());
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
    
    public List<Testpaper> findByCourseByStuid(int courseId, int stuId) {
        List<Testpaper> tem = em.createNativeQuery("select * from testpaper where course=" + courseId + " and stuid =" + stuId + " and testassignid is null order by score  ", Testpaper.class).getResultList();
        if (tem == null || tem.isEmpty()) {
            return null;
        } else {
            return tem;
        }
    }
}
