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
 * @author hgs
 */
@Entity
@Table(name = "CLASSINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classinfo.findAll", query = "SELECT c FROM Classinfo c"),
    @NamedQuery(name = "Classinfo.findById", query = "SELECT c FROM Classinfo c WHERE c.id = :id"),
    @NamedQuery(name = "Classinfo.findByClassname", query = "SELECT c FROM Classinfo c WHERE c.classname = :classname")})
public class Classinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "CLASSNAME")
    private String classname;
    @OneToMany(mappedBy = "classinfo")
    private List<Testassigninfom> testassigninfomList;
    @OneToMany(mappedBy = "classinfo")
    private List<Studentinfo> studentinfoList;
    @OneToMany(mappedBy = "classinfo")
    private List<Classquestiondifficult> classquestiondifficultList;
    @JoinColumn(name = "MAJOR", referencedColumnName = "ID")
    @ManyToOne
    private Major major;
    @OneToMany(mappedBy = "classinfo")
    private List<Teachercourseclass> teachercourseclassList;

    public Classinfo() {
    }

    public Classinfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @XmlTransient
    public List<Testassigninfom> getTestassigninfomList() {
        return testassigninfomList;
    }

    public void setTestassigninfomList(List<Testassigninfom> testassigninfomList) {
        this.testassigninfomList = testassigninfomList;
    }

    @XmlTransient
    public List<Studentinfo> getStudentinfoList() {
        return studentinfoList;
    }

    public void setStudentinfoList(List<Studentinfo> studentinfoList) {
        this.studentinfoList = studentinfoList;
    }

    @XmlTransient
    public List<Classquestiondifficult> getClassquestiondifficultList() {
        return classquestiondifficultList;
    }

    public void setClassquestiondifficultList(List<Classquestiondifficult> classquestiondifficultList) {
        this.classquestiondifficultList = classquestiondifficultList;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @XmlTransient
    public List<Teachercourseclass> getTeachercourseclassList() {
        return teachercourseclassList;
    }

    public void setTeachercourseclassList(List<Teachercourseclass> teachercourseclassList) {
        this.teachercourseclassList = teachercourseclassList;
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
        if (!(object instanceof Classinfo)) {
            return false;
        }
        Classinfo other = (Classinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Classinfo[ id=" + id + " ]";
    }
    
}
