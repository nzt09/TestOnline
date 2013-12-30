/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Teachercourseclass;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface TeachercourseclassFacadeLocal {

    void create(Teachercourseclass teachercourseclass);

    void edit(Teachercourseclass teachercourseclass);

    void remove(Teachercourseclass teachercourseclass);

    Teachercourseclass find(Object id);

    List<Teachercourseclass> findAll();

    List<Teachercourseclass> findRange(int[] range);

    int count();

    List<Teachercourseclass> findById(int id);
    
    List<Teachercourseclass> findByPersonId(String id);

}
