/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kemele
 */
@Entity(name = "choice")
@XmlRootElement
public class Choice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "last_modified")
    @Temporal(TemporalType.DATE)
    private Date lastModified;
    @JoinColumn(name = "placement_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Placement placementId;
    @JoinColumn(name = "idNumber", referencedColumnName = "idNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    private Student idNumber;
    @JoinColumn(name = "program_placement_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentPlacement departmentPlacementId;

    public Choice() {
    }

    public Choice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Placement getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Placement placementId) {
        this.placementId = placementId;
    }

    public Student getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Student idNumber) {
        this.idNumber = idNumber;
    }

    public DepartmentPlacement getDepartmentPlacementId() {
        return departmentPlacementId;
    }

    public void setDepartmentPlacementId(DepartmentPlacement departmentPlacementId) {
        this.departmentPlacementId = departmentPlacementId;
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
        if (!(object instanceof Choice)) {
            return false;
        }
        Choice other = (Choice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.placement.entity.Choice[ id=" + id + " ]";
    }
    
}
