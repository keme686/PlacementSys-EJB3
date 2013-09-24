package com.placement.entity;

import com.placement.entity.Choice;
import com.placement.entity.Department;
import com.placement.entity.Placement;
import com.placement.entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-13T10:40:52")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, String> region;
    public static volatile SingularAttribute<Student, Boolean> placementStatus;
    public static volatile SingularAttribute<Student, String> woreda;
    public static volatile SingularAttribute<Student, Users> username;
    public static volatile ListAttribute<Student, Choice> choiceList;
    public static volatile SingularAttribute<Student, String> gender;
    public static volatile SingularAttribute<Student, String> disability;
    public static volatile SingularAttribute<Student, String> firstName;
    public static volatile SingularAttribute<Student, String> placementReason;
    public static volatile SingularAttribute<Student, String> idNumber;
    public static volatile SingularAttribute<Student, String> middleName;
    public static volatile SingularAttribute<Student, String> lastName;
    public static volatile SingularAttribute<Student, Double> cgpa;
    public static volatile SingularAttribute<Student, Placement> placementId;
    public static volatile SingularAttribute<Student, Integer> classYear;
    public static volatile SingularAttribute<Student, String> country;
    public static volatile SingularAttribute<Student, String> email;
    public static volatile SingularAttribute<Student, String> eheeceRegistrationNumber;
    public static volatile SingularAttribute<Student, Double> eheeeceAverage;
    public static volatile SingularAttribute<Student, Double> preparatoryAverage;
    public static volatile SingularAttribute<Student, Department> departmentId;
    public static volatile SingularAttribute<Student, String> town;
    public static volatile SingularAttribute<Student, String> mobileNumber;
    public static volatile SingularAttribute<Student, String> zone;
    public static volatile SingularAttribute<Student, String> academicYear;
    public static volatile SingularAttribute<Student, Integer> semester;

}