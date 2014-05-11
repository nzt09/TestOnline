/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Teachercourseclass;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class TeachercourseclassFacade extends AbstractFacade<Teachercourseclass> implements TeachercourseclassFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeachercourseclassFacade() {
        super(Teachercourseclass.class);
    }

    public List<Teachercourseclass> findById(int id) {
        List<Teachercourseclass> tem = em.createNativeQuery("select * from teachercourseclass where classid=" + id + "", Teachercourseclass.class).getResultList();
        System.out.print("dsadasdddddddddd");
        return tem;
    }

    public List<Teachercourseclass> findByPersonId(String id) {
        List<Teachercourseclass> tem = em.createNativeQuery("select * from teachercourseclass where teacherId='" + id + "'", Teachercourseclass.class).getResultList();
        System.out.print("dsadasdddddddddd");
        return tem;
    }
    
    public List<Teachercourseclass> findByCouseIdTeaId(int courseId,String teacherID){
        List<Teachercourseclass> tem = em.createNativeQuery("select * from teachercourseclass where teacherId='" + teacherID + "'and courseid="+courseId+"", Teachercourseclass.class).getResultList();
        return tem;
    }
}
