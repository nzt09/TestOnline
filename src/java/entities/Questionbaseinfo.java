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
 * @author hgs
 */
@Entity
@Table(name = "QUESTIONBASEINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionbaseinfo.findAll", query = "SELECT q FROM Questionbaseinfo q"),
    @NamedQuery(name = "Questionbaseinfo.findById", query = "SELECT q FROM Questionbaseinfo q WHERE q.id = :id"),
    @NamedQuery(name = "Questionbaseinfo.findByStem", query = "SELECT q FROM Questionbaseinfo q WHERE q.stem = :stem"),
    @NamedQuery(name = "Questionbaseinfo.findByRightanswer", query = "SELECT q FROM Questionbaseinfo q WHERE q.rightanswer = :rightanswer"),
    @NamedQuery(name = "Questionbaseinfo.findByWronganswer", query = "SELECT q FROM Questionbaseinfo q WHERE q.wronganswer = :wronganswer"),
    @NamedQuery(name = "Questionbaseinfo.findByDifficulty", query = "SELECT q FROM Questionbaseinfo q WHERE q.difficulty = :difficulty"),
    @NamedQuery(name = "Questionbaseinfo.findByTestcaseresult", query = "SELECT q FROM Questionbaseinfo q WHERE q.testcaseresult = :testcaseresult"),
    @NamedQuery(name = "Questionbaseinfo.findByTestcasepara", query = "SELECT q FROM Questionbaseinfo q WHERE q.testcasepara = :testcasepara"),
    @NamedQuery(name = "Questionbaseinfo.findByAnalysis", query = "SELECT q FROM Questionbaseinfo q WHERE q.analysis = :analysis")})
public class Questionbaseinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20000)
    @Column(name = "STEM")
    private String stem;
    @Size(max = 10000)
    @Column(name = "RIGHTANSWER")
    private String rightanswer;
    @Size(max = 10000)
    @Column(name = "WRONGANSWER")
    private String wronganswer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIFFICULTY")
    private Double difficulty;
    @Size(max = 10000)
    @Column(name = "TESTCASERESULT")
    private String testcaseresult;
    @Size(max = 10000)
    @Column(name = "TESTCASEPARA")
    private String testcasepara;
    @Size(max = 1000)
    @Column(name = "ANALYSIS")
    private String analysis;
    @JoinColumn(name = "QUESTIONTYPE", referencedColumnName = "ID")
    @ManyToOne
    private Questiontypeinfo questiontypeinfo;
    @JoinColumn(name = "KNOWLEDGEID", referencedColumnName = "ID")
    @ManyToOne
    private Knowledge knowledge;

    public Questionbaseinfo() {
    }

    public Questionbaseinfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getRightanswer() {
        return rightanswer;
    }

    public void setRightanswer(String rightanswer) {
        this.rightanswer = rightanswer;
    }

    public String getWronganswer() {
        return wronganswer;
    }

    public void setWronganswer(String wronganswer) {
        this.wronganswer = wronganswer;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public String getTestcaseresult() {
        return testcaseresult;
    }

    public void setTestcaseresult(String testcaseresult) {
        this.testcaseresult = testcaseresult;
    }

    public String getTestcasepara() {
        return testcasepara;
    }

    public void setTestcasepara(String testcasepara) {
        this.testcasepara = testcasepara;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Questiontypeinfo getQuestiontypeinfo() {
        return questiontypeinfo;
    }

    public void setQuestiontypeinfo(Questiontypeinfo questiontypeinfo) {
        this.questiontypeinfo = questiontypeinfo;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
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
        if (!(object instanceof Questionbaseinfo)) {
            return false;
        }
        Questionbaseinfo other = (Questionbaseinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Questionbaseinfo[ id=" + id + " ]";
    }
    
}
