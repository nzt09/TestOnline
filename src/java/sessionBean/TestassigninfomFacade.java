/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Testassigninfom;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class TestassigninfomFacade extends AbstractFacade<Testassigninfom> implements TestassigninfomFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TestassigninfomFacade() {
        super(Testassigninfom.class);
    }

    public List<Testassigninfom> findConstrainRange(int[] range, int courseId, int classId) {
        List<Testassigninfom> tem = em.createNativeQuery("select * from Testassigninfom where courseId=" + courseId + " and classId=" + classId, Testassigninfom.class).getResultList();
//        System.out.println("select * from teacher where roleid=" + roleId + " and departmentid=" + departmentId);
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }

    public List<Testassigninfom> findCourseClass(int courseId, int classId) {
        List<Testassigninfom> tem = em.createNativeQuery("select * from Testassigninfom where courseId=" + courseId + " and classId=" + classId, Testassigninfom.class).getResultList();
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }

    public List<Testassigninfom> findRange(int[] range, int departmentId) {
        List<Testassigninfom> tem = em.createNativeQuery("select * from Testassigninfom where classId in(select distinct(classId) from departclass where department=" + departmentId+")", Testassigninfom.class).getResultList();
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }
}
