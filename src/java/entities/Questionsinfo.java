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
@Table(name = "QUESTIONSINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionsinfo.findAll", query = "SELECT q FROM Questionsinfo q"),
    @NamedQuery(name = "Questionsinfo.findById", query = "SELECT q FROM Questionsinfo q WHERE q.id = :id"),
    @NamedQuery(name = "Questionsinfo.findByContent", query = "SELECT q FROM Questionsinfo q WHERE q.content = :content"),
    @NamedQuery(name = "Questionsinfo.findByScore", query = "SELECT q FROM Questionsinfo q WHERE q.score = :score"),
    @NamedQuery(name = "Questionsinfo.findByDifficulty", query = "SELECT q FROM Questionsinfo q WHERE q.difficulty = :difficulty"),
    @NamedQuery(name = "Questionsinfo.findBySelections", query = "SELECT q FROM Questionsinfo q WHERE q.selections = :selections"),
    @NamedQuery(name = "Questionsinfo.findByAnswer", query = "SELECT q FROM Questionsinfo q WHERE q.answer = :answer"),
    @NamedQuery(name = "Questionsinfo.findByAveragetime", query = "SELECT q FROM Questionsinfo q WHERE q.averagetime = :averagetime"),
    @NamedQuery(name = "Questionsinfo.findByCode", query = "SELECT q FROM Questionsinfo q WHERE q.code = :code"),
    @NamedQuery(name = "Questionsinfo.findByInsequence", query = "SELECT q FROM Questionsinfo q WHERE q.insequence = :insequence"),
    @NamedQuery(name = "Questionsinfo.findByCount", query = "SELECT q FROM Questionsinfo q WHERE q.count = :count"),
    @NamedQuery(name = "Questionsinfo.findByTestcasepara", query = "SELECT q FROM Questionsinfo q WHERE q.testcasepara = :testcasepara"),
    @NamedQuery(name = "Questionsinfo.findByTestcaseresult", query = "SELECT q FROM Questionsinfo q WHERE q.testcaseresult = :testcaseresult"),
    @NamedQuery(name = "Questionsinfo.findByAnalysis", query = "SELECT q FROM Questionsinfo q WHERE q.analysis = :analysis")})
public class Questionsinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30000)
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "SCORE")
    private Integer score;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIFFICULTY")
    private Double difficulty;
    @Size(max = 5000)
    @Column(name = "SELECTIONS")
    private String selections;
    @Size(max = 5000)
    @Column(name = "ANSWER")
    private String answer;
    @Column(name = "AVERAGETIME")
    private Double averagetime;
    @Size(max = 30000)
    @Column(name = "CODE")
    private String code;
    @Column(name = "INSEQUENCE")
    private Integer insequence;
    @Column(name = "COUNT")
    private Double count;
    @Size(max = 2000)
    @Column(name = "TESTCASEPARA")
    private String testcasepara;
    @Size(max = 1000)
    @Column(name = "TESTCASERESULT")
    private String testcaseresult;
    @Size(max = 1000)
    @Column(name = "ANALYSIS")
    private String analysis;
    @JoinColumn(name = "QUESTIONTYPE", referencedColumnName = "ID")
    @ManyToOne
    private Questiontypeinfo questiontypeinfo;
    @OneToMany(mappedBy = "questionsinfo")
    private List<Classquestiondifficult> classquestiondifficultList;
    @OneToMany(mappedBy = "questionsinfo")
    private List<Question2knowledge> question2knowledgeList;

    public Questionsinfo() {
    }

    public Questionsinfo(Integer id) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public String getSelections() {
        return selections;
    }

    public void setSelections(String selections) {
        this.selections = selections;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getAveragetime() {
        return averagetime;
    }

    public void setAveragetime(Double averagetime) {
        this.averagetime = averagetime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getInsequence() {
        return insequence;
    }

    public void setInsequence(Integer insequence) {
        this.insequence = insequence;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getTestcasepara() {
        return testcasepara;
    }

    public void setTestcasepara(String testcasepara) {
        this.testcasepara = testcasepara;
    }

    public String getTestcaseresult() {
        return testcaseresult;
    }

    public void setTestcaseresult(String testcaseresult) {
        this.testcaseresult = testcaseresult;
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

    @XmlTransient
    public List<Classquestiondifficult> getClassquestiondifficultList() {
        return classquestiondifficultList;
    }

    public void setClassquestiondifficultList(List<Classquestiondifficult> classquestiondifficultList) {
        this.classquestiondifficultList = classquestiondifficultList;
    }

    @XmlTransient
    public List<Question2knowledge> getQuestion2knowledgeList() {
        return question2knowledgeList;
    }

    public void setQuestion2knowledgeList(List<Question2knowledge> question2knowledgeList) {
        this.question2knowledgeList = question2knowledgeList;
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
        if (!(object instanceof Questionsinfo)) {
            return false;
        }
        Questionsinfo other = (Questionsinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Questionsinfo[ id=" + id + " ]";
    }
    
}
