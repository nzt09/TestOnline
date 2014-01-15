/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hgs
 */
@Entity
@Table(name = "TESTPAPER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testpaper.findAll", query = "SELECT t FROM Testpaper t"),
    @NamedQuery(name = "Testpaper.findById", query = "SELECT t FROM Testpaper t WHERE t.id = :id"),
    @NamedQuery(name = "Testpaper.findByContent", query = "SELECT t FROM Testpaper t WHERE t.content = :content"),
    @NamedQuery(name = "Testpaper.findByWrongnum", query = "SELECT t FROM Testpaper t WHERE t.wrongnum = :wrongnum"),
    @NamedQuery(name = "Testpaper.findBySuminterval", query = "SELECT t FROM Testpaper t WHERE t.suminterval = :suminterval"),
    @NamedQuery(name = "Testpaper.findByStarttime", query = "SELECT t FROM Testpaper t WHERE t.starttime = :starttime"),
    @NamedQuery(name = "Testpaper.findByScore", query = "SELECT t FROM Testpaper t WHERE t.score = :score"),
    @NamedQuery(name = "Testpaper.findByAnswer", query = "SELECT t FROM Testpaper t WHERE t.answer = :answer"),
    @NamedQuery(name = "Testpaper.findByCheckstate", query = "SELECT t FROM Testpaper t WHERE t.checkstate = :checkstate")})
public class Testpaper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 1000)
    @Column(name = "CONTENT")
    private String content;
    @Size(max = 500)
    @Column(name = "WRONGNUM")
    private String wrongnum;
    @Column(name = "SUMINTERVAL")
    private Integer suminterval;
    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;
    @Column(name = "SCORE")
    private Integer score;
    @Size(max = 20000)
    @Column(name = "ANSWER")
    private String answer;
    @Column(name = "CHECKSTATE")
    private Integer checkstate;
    @JoinColumn(name = "TESTASSIGNID", referencedColumnName = "ID")
    @ManyToOne
    private Testassigninfom testassigninfom;
    @JoinColumn(name = "STUID", referencedColumnName = "ID")
    @ManyToOne
    private Studentinfo studentinfo;
    @JoinColumn(name = "COURSE", referencedColumnName = "ID")
    @ManyToOne
    private Courseinfo courseinfo;

    public Testpaper() {
    }

    public Testpaper(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWrongnum() {
        return wrongnum;
    }

    public void setWrongnum(String wrongnum) {
        this.wrongnum = wrongnum;
    }

    public Integer getSuminterval() {
        return suminterval;
    }

    public void setSuminterval(Integer suminterval) {
        this.suminterval = suminterval;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(Integer checkstate) {
        this.checkstate = checkstate;
    }

    public Testassigninfom getTestassigninfom() {
        return testassigninfom;
    }

    public void setTestassigninfom(Testassigninfom testassigninfom) {
        this.testassigninfom = testassigninfom;
    }

    public Studentinfo getStudentinfo() {
        return studentinfo;
    }

    public void setStudentinfo(Studentinfo studentinfo) {
        this.studentinfo = studentinfo;
    }

    public Courseinfo getCourseinfo() {
        return courseinfo;
    }

    public void setCourseinfo(Courseinfo courseinfo) {
        this.courseinfo = courseinfo;
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
        if (!(object instanceof Testpaper)) {
            return false;
        }
        Testpaper other = (Testpaper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Testpaper[ id=" + id + " ]";
    }
    
}
