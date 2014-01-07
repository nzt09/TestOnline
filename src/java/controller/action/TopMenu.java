/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.TeacherController;
import entities.Resourceinfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.registry.infomodel.User;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class TopMenu implements Serializable {
    
    
    @Inject
    private TeacherController teacherController;
    @Inject
    private PublicFields publicFields;

    private Set<Entry<Resourceinfo, List<Resourceinfo>>> resWithChildrenMap;
    Resourceinfo resource = new Resourceinfo();
    

    public  Set<Entry<Resourceinfo, List<Resourceinfo>>> getResWithChildrenList() {
        if (null == this.resWithChildrenMap || this.resWithChildrenMap.isEmpty()) {
            System.out.println(teacherController.getCurrent().getRolesinfo().getId());
            resWithChildrenMap = publicFields.getReslistMap().get(teacherController.getCurrent().getRolesinfo().getId()).entrySet();
        }
        return this.resWithChildrenMap;
    }
}
