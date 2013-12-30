/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Knowledge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface MyKnowledgeFacadeLocal {

    void create(Knowledge knowledge);

    void edit(Knowledge knowledge);

    void remove(Knowledge knowledge);

    Knowledge find(Object id);

    List<Knowledge> findAll();

    List<Knowledge> findRange(int[] range);

    int count();

    List<Knowledge> getKnowledgeList(String sql);

    List<Knowledge> findByChapterId(int id);

    List<Knowledge> findByParentId(int id);

    List<Knowledge> nativeQuery(String sqlString);
}