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
@Table(name = "STUDENTINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studentinfo.findAll", query = "SELECT s FROM Studentinfo s"),
    @NamedQuery(name = "Studentinfo.findById", query = "SELECT s FROM Studentinfo s WHERE s.id = :id"),
    @NamedQuery(name = "Studentinfo.findByName", query = "SELECT s FROM Studentinfo s WHERE s.name = :name"),
    @NamedQuery(name = "Studentinfo.findByEmail", query = "SELECT s FROM Studentinfo s WHERE s.email = :email"),
    @NamedQuery(name = "Studentinfo.findByPassword", query = "SELECT s FROM Studentinfo s WHERE s.password = :password"),
    @NamedQuery(name = "Studentinfo.findByStuno", query = "SELECT s FROM Studentinfo s WHERE s.stuno = :stuno")})
public class Studentinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="电子邮件无效")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 20)
    @Column(name = "STUNO")
    private String stuno;
    @OneToMany(mappedBy = "studentinfo")
    private List<Testpaper> testpaperList;
    @JoinColumn(name = "CLASSID", referencedColumnName = "ID")
    @ManyToOne
    private Classinfo classinfo;

    public Studentinfo() {
    }

    public Studentinfo(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    @XmlTransient
    public List<Testpaper> getTestpaperList() {
        return testpaperList;
    }

    public void setTestpaperList(List<Testpaper> testpaperList) {
        this.testpaperList = testpaperList;
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
        if (!(object instanceof Studentinfo)) {
            return false;
        }
        Studentinfo other = (Studentinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Studentinfo[ id=" + id + " ]";
    }
    
}
