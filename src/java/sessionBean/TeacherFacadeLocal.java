/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Teacher;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface TeacherFacadeLocal {

    void create(Teacher teacher);

    void edit(Teacher teacher);

    void remove(Teacher teacher);

    Teacher find(Object id);

    List<Teacher> findAll();

    List<Teacher> findRange(int[] range);

    int count();

    List<Teacher> findConstrainRange(int[] range, int roleId, int departmentId);

    Teacher findByIdPassword(String userId, String password);

     List<Teacher> findByRoleId(int roleId);

     List<Teacher> findByPersonId(int[] range,String teacherId);
}
