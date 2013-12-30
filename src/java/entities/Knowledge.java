/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "KNOWLEDGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Knowledge.findAll", query = "SELECT k FROM Knowledge k"),
    @NamedQuery(name = "Knowledge.findById", query = "SELECT k FROM Knowledge k WHERE k.id = :id"),
    @NamedQuery(name = "Knowledge.findByName", query = "SELECT k FROM Knowledge k WHERE k.name = :name")})
public class Knowledge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "knowledge")
    private List<Questionsinfo> questionsinfoList;
    @OneToMany(mappedBy = "knowledge")
    private List<Questionbaseinfo> questionbaseinfoList;
    @OneToMany(mappedBy = "knowledge")
    private List<Knowledge> knowledgeList;
    @JoinColumn(name = "PARENTID", referencedColumnName = "ID")
    @ManyToOne
    private Knowledge knowledge;
    @JoinColumn(name = "CHAPTER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Chapterinfo chapterinfo;

    public Knowledge() {
    }

    public Knowledge(Integer id) {
        this.id = id;
    }

    public Knowledge(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlTransient
    public List<Questionsinfo> getQuestionsinfoList() {
        return questionsinfoList;
    }

    public void setQuestionsinfoList(List<Questionsinfo> questionsinfoList) {
        this.questionsinfoList = questionsinfoList;
    }

    @XmlTransient
    public List<Questionbaseinfo> getQuestionbaseinfoList() {
        return questionbaseinfoList;
    }

    public void setQuestionbaseinfoList(List<Questionbaseinfo> questionbaseinfoList) {
        this.questionbaseinfoList = questionbaseinfoList;
    }

    @XmlTransient
    public List<Knowledge> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(List<Knowledge> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public Chapterinfo getChapterinfo() {
        return chapterinfo;
    }

    public void setChapterinfo(Chapterinfo chapterinfo) {
        this.chapterinfo = chapterinfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Knowledge)) {
            return false;
        }
        Knowledge other = (Knowledge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Knowledge[ id=" + id + " ]";
    }
    
}
