/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kemele
 */
@Entity(name = "rule")
@XmlRootElement
public class Rule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "top_percentage")
    private Double topPercentage;
    @Column(name = "female_percentage")
    private Double femalePercentage;
    @Column(name = "region_percentage")
    private Double regionPercentage;
    @Column(name = "disability_percentage")
    private Double disabilityPercentage;
    @Column(name = "cut_point")
    private Double cutPoint;

    public Rule() {
    }

    public Rule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTopPercentage() {
        return topPercentage;
    }

    public void setTopPercentage(Double topPercentage) {
        this.topPercentage = topPercentage;
    }

    public Double getFemalePercentage() {
        return femalePercentage;
    }

    public void setFemalePercentage(Double femalePercentage) {
        this.femalePercentage = femalePercentage;
    }

    public Double getRegionPercentage() {
        return regionPercentage;
    }

    public void setRegionPercentage(Double regionPercentage) {
        this.regionPercentage = regionPercentage;
    }

    public Double getDisabilityPercentage() {
        return disabilityPercentage;
    }

    public void setDisabilityPercentage(Double disabilityPercentage) {
        this.disabilityPercentage = disabilityPercentage;
    }

    public Double getCutPoint() {
        return cutPoint;
    }

    public void setCutPoint(Double cutPoint) {
        this.cutPoint = cutPoint;
    }

//    @XmlTransient
//    public List<Placement> getPlacementList() {
//        return placementList;
//    }
//
//    public void setPlacementList(List<Placement> placementList) {
//        this.placementList = placementList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rule)) {
            return false;
        }
        Rule other = (Rule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CGPA-" + (getTopPercentage())
                    + "%, Female-" + (getFemalePercentage())
                    + "%, Region-" + (getRegionPercentage())
                    + "%, Disability-" + (getDisabilityPercentage())
                    + "%, CGPA Cut Point-" + (getCutPoint());
    }
    
}
