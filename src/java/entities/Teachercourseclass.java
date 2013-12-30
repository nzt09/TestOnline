/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TEACHERCOURSECLASS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teachercourseclass.findAll", query = "SELECT t FROM Teachercourseclass t"),
    @NamedQuery(name = "Teachercourseclass.findById", query = "SELECT t FROM Teachercourseclass t WHERE t.id = :id"),
    @NamedQuery(name = "Teachercourseclass.findByTerm", query = "SELECT t FROM Teachercourseclass t WHERE t.term = :term")})
public class Teachercourseclass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 15)
    @Column(name = "TERM")
    private String term;
    @JoinColumn(name = "TEACHERID", referencedColumnName = "PERSONID")
    @ManyToOne
    private Teacher teacher;
    @JoinColumn(name = "COURSEID", referencedColumnName = "ID")
    @ManyToOne
    private Courseinfo courseinfo;
    @JoinColumn(name = "CLASSID", referencedColumnName = "ID")
    @ManyToOne
    private Classinfo classinfo;

    public Teachercourseclass() {
    }

    public Teachercourseclass(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Courseinfo getCourseinfo() {
        return courseinfo;
    }

    public void setCourseinfo(Courseinfo courseinfo) {
        this.courseinfo = courseinfo;
    }

    public Classinfo getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(Classinfo classinfo) {
        this.classinfo = classinfo;
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
        if (!(object instanceof Teachercourseclass)) {
            return false;
        }
        Teachercourseclass other = (Teachercourseclass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Teachercourseclass[ id=" + id + " ]";
    }
    
}
