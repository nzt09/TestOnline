/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Chapterinfo;
import entities.Knowledge;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import sessionBean.MyKnowledgeFacadeLocal;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class WrappedChapterinfo implements java.io.Serializable{
    @EJB
    private MyKnowledgeFacadeLocal myknowledgeEjb;
    
    private Chapterinfo cc;
    private List<WrappedKnowledge> CCParents;
    private List<WrappedKnowledge> CCChildren;
    
    
    public WrappedChapterinfo(){
        
    }
    public WrappedChapterinfo(Chapterinfo cc) {
        this.cc = cc;
    }
    
    public Chapterinfo getCc() {
        return cc;
    }
    
    public void setCc(Chapterinfo cc) {
        this.cc = cc;
    }

    /**
     * @return the noLeavesNode
     */
    public List<WrappedKnowledge> getCCParents() {
//        if (null == this.CCParents || CCParents.isEmpty()) {
            this.CCParents = new LinkedList<>();
            this.CCChildren = new LinkedList<>();
            List<Knowledge> wkList = cc.getKnowledgeList();//myknowledgeEjb.getKnowledgeList("select * from knowledge where chapter="+cc.getId());
            if (wkList.size() > 0) {
                for (int i = 0; i < wkList.size(); i++) {
                    if (wkList.get(i).getKnowledgeList().size() > 0) {
                        this.CCParents.add(new WrappedKnowledge(wkList.get(i)));
                    } else {
                        this.CCChildren.add(new WrappedKnowledge(wkList.get(i)));
                    }
                }
            }
//        }
        return CCParents;
    }
    
     public List<WrappedKnowledge> getCCChildren() {
//        if (null == this.CCParents || CCParents.isEmpty()) {
            this.CCParents = new LinkedList<>();
            this.CCChildren = new LinkedList<>();
            List<Knowledge> wkList = cc.getKnowledgeList();
            if (wkList.size() > 0) {
                for (int i = 0; i < wkList.size(); i++) {
                    if (wkList.get(i).getKnowledgeList().size() > 0) {
                        this.CCParents.add(new WrappedKnowledge(wkList.get(i)));
                    } else {
                        this.CCChildren.add(new WrappedKnowledge(wkList.get(i)));
                    }
                }
            }
//        }
        return CCChildren;
    }

    
    /**
     * @param noLeavesNode the noLeavesNode to set
     */
    public void setCCChildren(List<WrappedKnowledge> noLeavesNode) {
        this.CCParents = noLeavesNode;
    }

    /**
     * @return the leavesNode
     */
    public List<WrappedKnowledge> getLeavesNode() {
        return CCChildren;
    }

    /**
     * @param leavesNode the leavesNode to set
     */
    public void setLeavesNode(List<WrappedKnowledge> leavesNode) {
        this.CCChildren = leavesNode;
    }
}
