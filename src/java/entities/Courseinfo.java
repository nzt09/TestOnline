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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "COURSEINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Courseinfo.findAll", query = "SELECT c FROM Courseinfo c"),
    @NamedQuery(name = "Courseinfo.findById", query = "SELECT c FROM Courseinfo c WHERE c.id = :id"),
    @NamedQuery(name = "Courseinfo.findByName", query = "SELECT c FROM Courseinfo c WHERE c.name = :name")})
public class Courseinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "courseinfo")
    private List<Testpaper> testpaperList;
    @OneToMany(mappedBy = "courseinfo")
    private List<Chapterinfo> chapterinfoList;
    @OneToMany(mappedBy = "courseinfo")
    private List<Testassigninfom> testassigninfomList;
    @OneToMany(mappedBy = "courseinfo")
    private List<Teachercourseclass> teachercourseclassList;
    @OneToMany(mappedBy = "courseinfo")
    private List<Relationcourmaj> relationcourmajList;
    @JoinColumn(name = "SUBJECTID", referencedColumnName = "ID")
    @ManyToOne
    private Subjectinfo subjectinfo;
    @JoinColumn(name = "MAJOR", referencedColumnName = "ID")
    @ManyToOne
    private Major major;

    public Courseinfo() {
    }

    public Courseinfo(Integer id) {
        this.id = id;
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
    public List<Testpaper> getTestpaperList() {
        return testpaperList;
    }

    public void setTestpaperList(List<Testpaper> testpaperList) {
        this.testpaperList = testpaperList;
    }

    @XmlTransient
    public List<Chapterinfo> getChapterinfoList() {
        return chapterinfoList;
    }

    public void setChapterinfoList(List<Chapterinfo> chapterinfoList) {
        this.chapterinfoList = chapterinfoList;
    }

    @XmlTransient
    public List<Testassigninfom> getTestassigninfomList() {
        return testassigninfomList;
    }

    public void setTestassigninfomList(List<Testassigninfom> testassigninfomList) {
        this.testassigninfomList = testassigninfomList;
    }

    @XmlTransient
    public List<Teachercourseclass> getTeachercourseclassList() {
        return teachercourseclassList;
    }

    public void setTeachercourseclassList(List<Teachercourseclass> teachercourseclassList) {
        this.teachercourseclassList = teachercourseclassList;
    }

    @XmlTransient
    public List<Relationcourmaj> getRelationcourmajList() {
        return relationcourmajList;
    }

    public void setRelationcourmajList(List<Relationcourmaj> relationcourmajList) {
        this.relationcourmajList = relationcourmajList;
    }

    public Subjectinfo getSubjectinfo() {
        return subjectinfo;
    }

    public void setSubjectinfo(Subjectinfo subjectinfo) {
        this.subjectinfo = subjectinfo;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
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
        if (!(object instanceof Courseinfo)) {
            return false;
        }
        Courseinfo other = (Courseinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Courseinfo[ id=" + id + " ]";
    }
    
}
