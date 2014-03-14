/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Testassigninfom;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface TestassigninfomFacadeLocal {

    void create(Testassigninfom testassigninfom);

    void edit(Testassigninfom testassigninfom);

    void remove(Testassigninfom testassigninfom);

    Testassigninfom find(Object id);

    List<Testassigninfom> findAll();

    List<Testassigninfom> findRange(int[] range);

    int count();

    List<Testassigninfom> findConstrainRange(int[] range, int courseId, int classId);

    List<Testassigninfom> findCourseClass(int courseId, int classId);

}
