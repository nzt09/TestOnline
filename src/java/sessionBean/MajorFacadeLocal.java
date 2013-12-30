/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Major;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface MajorFacadeLocal {

    void create(Major major);

    void edit(Major major);

    void remove(Major major);

    Major find(Object id);

    List<Major> findAll();

    List<Major> findRange(int[] range);

    int count();

    List<Major> findByDepartment(int id);
    
}
