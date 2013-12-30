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
@Table(name = "TEACHER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t"),
    @NamedQuery(name = "Teacher.findByPersonid", query = "SELECT t FROM Teacher t WHERE t.personid = :personid"),
    @NamedQuery(name = "Teacher.findByName", query = "SELECT t FROM Teacher t WHERE t.name = :name"),
    @NamedQuery(name = "Teacher.findByPassword", query = "SELECT t FROM Teacher t WHERE t.password = :password")})
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PERSONID")
    private String personid;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(mappedBy = "teacher")
    private List<Testassigninfom> testassigninfomList;
    @JoinColumn(name = "ROLEID", referencedColumnName = "ID")
    @ManyToOne
    private Rolesinfo rolesinfo;
    @JoinColumn(name = "DEPARTMENTID", referencedColumnName = "ID")
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "teacher")
    private List<Teachercourseclass> teachercourseclassList;

    public Teacher() {
    }

    public Teacher(String personid) {
        this.personid = personid;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Testassigninfom> getTestassigninfomList() {
        return testassigninfomList;
    }

    public void setTestassigninfomList(List<Testassigninfom> testassigninfomList) {
        this.testassigninfomList = testassigninfomList;
    }

    public Rolesinfo getRolesinfo() {
        return rolesinfo;
    }

    public void setRolesinfo(Rolesinfo rolesinfo) {
        this.rolesinfo = rolesinfo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        hash += (personid != null ? personid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) object;
        if ((this.personid == null && other.personid != null) || (this.personid != null && !this.personid.equals(other.personid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Teacher[ personid=" + personid + " ]";
    }
    
}
