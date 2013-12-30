/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Classroom;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface ClassroomFacadeLocal {

    void create(Classroom classroom);

    void edit(Classroom classroom);

    void remove(Classroom classroom);

    Classroom find(Object id);

    List<Classroom> findAll();

    List<Classroom> findRange(int[] range);

    int count();
    
}
