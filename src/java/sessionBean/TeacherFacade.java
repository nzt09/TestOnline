/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Teacher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class TeacherFacade extends AbstractFacade<Teacher> implements TeacherFacadeLocal {

    @PersistenceContext(unitName = "TestOnlineFree-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeacherFacade() {
        super(Teacher.class);
    }

    public List<Teacher> findConstrainRange(int[] range, int roleId, int departmentId) {
        List<Teacher> tem = em.createNativeQuery("select * from teacher where roleid=" + roleId + " and departmentid=" + departmentId, Teacher.class).getResultList();
//        System.out.println("select * from teacher where roleid=" + roleId + " and departmentid=" + departmentId);
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }

    public Teacher findByIdPassword(String userId, String password) {
        List<Teacher> tem = em.createNativeQuery("select * from teacher where  personId='" + userId + "'and password='" + password + "'", Teacher.class).getResultList();
        if (null == tem || tem.isEmpty()) {
            return new Teacher();
        } else {
            return (Teacher) tem.get(0);
        }
    }
 public List<Teacher> findByRoleId(int roleId) {
        List<Teacher> tem = em.createNativeQuery("select * from teacher where roleid=" + roleId + "", Teacher.class).getResultList();
        if (tem.isEmpty()) {
            return null;
        }
        return tem;
    }
  
    

}
