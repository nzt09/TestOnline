/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CHAPTERINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chapterinfo.findAll", query = "SELECT c FROM Chapterinfo c"),
    @NamedQuery(name = "Chapterinfo.findById", query = "SELECT c FROM Chapterinfo c WHERE c.id = :id"),
    @NamedQuery(name = "Chapterinfo.findByChapternum", query = "SELECT c FROM Chapterinfo c WHERE c.chapternum = :chapternum"),
    @NamedQuery(name = "Chapterinfo.findByName", query = "SELECT c FROM Chapterinfo c WHERE c.name = :name")})
public class Chapterinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CHAPTERNUM")
    private Integer chapternum;
    @Size(max = 20)
    @Column(name = "NAME")
    private String name;
    @JoinColumn(name = "COURSE", referencedColumnName = "ID")
    @ManyToOne
    private Courseinfo courseinfo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapterinfo")
    private List<Knowledge> knowledgeList;

    public Chapterinfo() {
    }

    public Chapterinfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapternum() {
        return chapternum;
    }

    public void setChapternum(Integer chapternum) {
        this.chapternum = chapternum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Courseinfo getCourseinfo() {
        return courseinfo;
    }

    public void setCourseinfo(Courseinfo courseinfo) {
        this.courseinfo = courseinfo;
    }

    @XmlTransient
    public List<Knowledge> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(List<Knowledge> knowledgeList) {
        this.knowledgeList = knowledgeList;
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
        if (!(object instanceof Chapterinfo)) {
            return false;
        }
        Chapterinfo other = (Chapterinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Chapterinfo[ id=" + id + " ]";
    }
    
}
