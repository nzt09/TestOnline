/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Testpaper;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface TestpaperFacadeLocal {

    void create(Testpaper testpaper);

    void edit(Testpaper testpaper);

    void remove(Testpaper testpaper);

    Testpaper find(Object id);

    List<Testpaper> findAll();

    List<Testpaper> findRange(int[] range);

    int count();
    
    List<Testpaper> findByStuId(int stuId);
    
    List<Testpaper> findByCourseBystuid(int courseId,int classId );
    
}
