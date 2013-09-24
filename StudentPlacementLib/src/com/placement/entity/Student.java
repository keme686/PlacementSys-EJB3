/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author kemele
 */
@Entity(name = "student")
@XmlRootElement
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idNumber")
    private String idNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "disability")
    private String disability;
    @Column(name = "class_year")
    private Integer classYear;
    @Column(name = "semester")
    private Integer semester;   
    @Column(name = "cgpa")
    private Double cgpa;
    @Column(name = "preparatory_average")
    private Double preparatoryAverage;
    @Column(name = "eheeece_average")
    private Double eheeeceAverage;
    @Column(name = "email")
    private String email;
    @Column(name = "academic_year")
    private String academicYear;
    @Column(name = "eheece_registration_number")
    private String eheeceRegistrationNumber;
    @Column(name = "country")
    private String country;
    @Column(name = "region")
    private String region;
    @Column(name = "zone")
    private String zone;
    @Column(name = "woreda")
    private String woreda;
    @Column(name = "town")
    private String town;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "placement_status")
    private Boolean placementStatus;
    @Column(name = "placement_reason")
    private String placementReason;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users username;
    @JoinColumn(name = "placement_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Placement placementId;
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Department departmentId;
    @OneToMany(mappedBy = "idNumber", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Choice> choiceList;
    @Transient
    private String fullName;
     
    public Student() {
    }

    public Student(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public Integer getClassYear() {
        return classYear;
    }

    public void setClassYear(Integer classYear) {
        this.classYear = classYear;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Double getPreparatoryAverage() {
        return preparatoryAverage;
    }

    public void setPreparatoryAverage(Double preparatoryAverage) {
        this.preparatoryAverage = preparatoryAverage;
    }

    public Double getEheeeceAverage() {
        return eheeeceAverage;
    }

    public void setEheeeceAverage(Double eheeeceAverage) {
        this.eheeeceAverage = eheeeceAverage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getEheeceRegistrationNumber() {
        return eheeceRegistrationNumber;
    }

    public void setEheeceRegistrationNumber(String eheeceRegistrationNumber) {
        this.eheeceRegistrationNumber = eheeceRegistrationNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWoreda() {
        return woreda;
    }

    public void setWoreda(String woreda) {
        this.woreda = woreda;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getPlacementStatus() {
        return placementStatus;
    }

    public void setPlacementStatus(Boolean placementStatus) {
        this.placementStatus = placementStatus;
    }

    public String getPlacementReason() {
        return placementReason;
    }

    public void setPlacementReason(String placementReason) {
        this.placementReason = placementReason;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    public Placement getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Placement placementId) {
        this.placementId = placementId;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    @XmlTransient    
    public List<Choice> getChoiceList() {        
        return choiceList;
    }

    public void setChoiceList(List<Choice> choiceList) {
        this.choiceList = choiceList;
    }

    public String getFullName(){
        return this.getFirstName() + " " + this.getMiddleName() + " "+ this.getLastName();
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNumber != null ? idNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.idNumber == null && other.idNumber != null) || (this.idNumber != null && !this.idNumber.equals(other.idNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.placement.entity.Student[ idNumber=" + idNumber + " ]";
    }
    
}
