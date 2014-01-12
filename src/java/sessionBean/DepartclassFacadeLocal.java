/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Departclass;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface DepartclassFacadeLocal {

    void create(Departclass departclass);

    void edit(Departclass departclass);

    void remove(Departclass departclass);

    Departclass find(Object id);

    List<Departclass> findAll();

    List<Departclass> findRange( int[] range,int departmentId,int classId,int majorId);

    int count(int classId,int departmentId,int majorId);
    
}
