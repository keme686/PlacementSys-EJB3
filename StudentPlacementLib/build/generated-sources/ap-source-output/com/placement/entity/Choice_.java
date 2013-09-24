package com.placement.entity;

import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
import com.placement.entity.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-13T10:40:52")
@StaticMetamodel(Choice.class)
public class Choice_ { 

    public static volatile SingularAttribute<Choice, Integer> id;
    public static volatile SingularAttribute<Choice, Integer> rank;
    public static volatile SingularAttribute<Choice, Date> lastModified;
    public static volatile SingularAttribute<Choice, Placement> placementId;
    public static volatile SingularAttribute<Choice, DepartmentPlacement> departmentPlacementId;
    public static volatile SingularAttribute<Choice, Student> idNumber;

}