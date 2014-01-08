/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import entities.Resourceinfo;
import entities.Rolesinfo;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import sessionBean.ResourceinfoFacadeLocal;
import sessionBean.RolesinfoFacadeLocal;

/**
 *
 * @author Administrator
 */
@Named
@ApplicationScoped
public class PublicFields implements java.io.Serializable {
    
    
    @EJB
    private  RolesinfoFacadeLocal rolesinfoFacade;
    @EJB
    private  ResourceinfoFacadeLocal resourceinfoFacade;

    private  LinkedHashMap<Integer, HashMap<Resourceinfo, List<Resourceinfo>>> ResourcelistMap;
//每个角色对应的功能菜单

    public  void calcuListResList() {
        //获得角色种类，把每个角色的一个List放到Map里作为一个元素
        
        List<Rolesinfo> roleList = rolesinfoFacade.findAll();
        System.out.println(roleList.get(0).getResouceids()+"ggggggggggggg");
        
        ResourcelistMap = new LinkedHashMap<>();
       
        for (int i = 0; i < 2; i++) {//准备第个角色的功能菜单;
            //准备父菜单
            
            String temSqlString = "select * from RESOURCEINFO where id in (" + roleList.get(i).getResouceids() + ") and parentid is null ";
            List<Resourceinfo> parentResource = resourceinfoFacade.findByResourceinfo(temSqlString);
            System.out.println(parentResource.toString());
            //为每个父菜单准备子菜单
            LinkedHashMap<Resourceinfo, List<Resourceinfo>> menu = new LinkedHashMap();
            for (int j = 0; j < parentResource.size(); j++) {
                System.out.println("rrrrrrrrrrrrrr"+parentResource.get(j).getId());
                System.out.println("ppppppppppppp"+roleList.get(i).getResouceids());
                String temChildSqlString = "select * from RESOURCEINFO where parentid=" + parentResource.get(j).getId() + " and id in (" + roleList.get(i).getResouceids() + ")  ";
                System.out.println(temChildSqlString);
                List<Resourceinfo> childrenResource = resourceinfoFacade.findByResourceinfo(temChildSqlString);
                
                menu.put(parentResource.get(j), childrenResource);
            }
            ResourcelistMap.put(roleList.get(i).getId(), menu);
        }
    }

    public LinkedHashMap<Integer, HashMap<Resourceinfo, List<Resourceinfo>>> getReslistMap() {
        if (null == ResourcelistMap || ResourcelistMap.isEmpty()) {
            calcuListResList();
        }
        return ResourcelistMap;
    }
}
