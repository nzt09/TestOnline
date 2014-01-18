/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hgs
 */
@Entity
@Table(name = "TESTASSIGNINFOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testassigninfom.findAll", query = "SELECT t FROM Testassigninfom t"),
    @NamedQuery(name = "Testassigninfom.findById", query = "SELECT t FROM Testassigninfom t WHERE t.id = :id"),
    @NamedQuery(name = "Testassigninfom.findByTesttime", query = "SELECT t FROM Testassigninfom t WHERE t.testtime = :testtime"),
    @NamedQuery(name = "Testassigninfom.findByTestinterval", query = "SELECT t FROM Testassigninfom t WHERE t.testinterval = :testinterval"),
    @NamedQuery(name = "Testassigninfom.findByTestdifficulty", query = "SELECT t FROM Testassigninfom t WHERE t.testdifficulty = :testdifficulty"),
    @NamedQuery(name = "Testassigninfom.findByIpstart", query = "SELECT t FROM Testassigninfom t WHERE t.ipstart = :ipstart"),
    @NamedQuery(name = "Testassigninfom.findByIpend", query = "SELECT t FROM Testassigninfom t WHERE t.ipend = :ipend"),
    @NamedQuery(name = "Testassigninfom.findByNotice", query = "SELECT t FROM Testassigninfom t WHERE t.notice = :notice"),
    @NamedQuery(name = "Testassigninfom.findByNoticescore", query = "SELECT t FROM Testassigninfom t WHERE t.noticescore = :noticescore"),
    @NamedQuery(name = "Testassigninfom.findByAveragetime", query = "SELECT t FROM Testassigninfom t WHERE t.averagetime = :averagetime")})
public class Testassigninfom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TESTTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testtime;
    @Column(name = "TESTINTERVAL")
    private Integer testinterval;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TESTDIFFICULTY")
    private Double testdifficulty;
    @Size(max = 15)
    @Column(name = "IPSTART")
    private String ipstart;
    @Size(max = 15)
    @Column(name = "IPEND")
    private String ipend;
    @Size(max = 4000)
    @Column(name = "NOTICE")
    private String notice;
    @Column(name = "NOTICESCORE")
    private Integer noticescore;
    @Column(name = "AVERAGETIME")
    private Integer averagetime;
    @OneToMany(mappedBy = "testassigninfom")
    private List<Testpaper> testpaperList;
    @JoinColumn(name = "TEACHER", referencedColumnName = "PERSONID")
    @ManyToOne
    private Teacher teacher;
    @JoinColumn(name = "COURSEID", referencedColumnName = "ID")
    @ManyToOne
    private Courseinfo courseinfo;
    @JoinColumn(name = "CLASSROOM", referencedColumnName = "ID")
    @ManyToOne
    private Classroom classroom;
    @JoinColumn(name = "CLASSID", referencedColumnName = "ID")
    @ManyToOne
    private Classinfo classinfo;

    public Testassigninfom() {
    }

    public Testassigninfom(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }

    public Integer getTestinterval() {
        return testinterval;
    }

    public void setTestinterval(Integer testinterval) {
        this.testinterval = testinterval;
    }

    public Double getTestdifficulty() {
        return testdifficulty;
    }

    public void setTestdifficulty(Double testdifficulty) {
        this.testdifficulty = testdifficulty;
    }

    public String getIpstart() {
        return ipstart;
    }

    public void setIpstart(String ipstart) {
        this.ipstart = ipstart;
    }

    public String getIpend() {
        return ipend;
    }

    public void setIpend(String ipend) {
        this.ipend = ipend;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getNoticescore() {
        return noticescore;
    }

    public void setNoticescore(Integer noticescore) {
        this.noticescore = noticescore;
    }

    public Integer getAveragetime() {
        return averagetime;
    }

    public void setAveragetime(Integer averagetime) {
        this.averagetime = averagetime;
    }

    @XmlTransient
    public List<Testpaper> getTestpaperList() {
        return testpaperList;
    }

    public void setTestpaperList(List<Testpaper> testpaperList) {
        this.testpaperList = testpaperList;
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

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
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
        if (!(object instanceof Testassigninfom)) {
            return false;
        }
        Testassigninfom other = (Testassigninfom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Testassigninfom[ id=" + id + " ]";
    }
    
}
