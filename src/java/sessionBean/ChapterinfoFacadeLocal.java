/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Chapterinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface ChapterinfoFacadeLocal {

    void create(Chapterinfo chapterinfo);

    void edit(Chapterinfo chapterinfo);

    void remove(Chapterinfo chapterinfo);

    Chapterinfo find(Object id);

    List<Chapterinfo> findAll();

    List<Chapterinfo> findRange(int[] range);

    int count();

    List<Chapterinfo> findByCourseId(int id);
}
