/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Choice;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
import com.placement.entity.Region;
import com.placement.entity.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author kemele
 */
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class StudentPlacementBL implements StudentPlacementBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;
    List<Student> students = null;
    List<Student> aboveCutPointStudents = new ArrayList<Student>();
    List<Student> belowCutPointStudents = new ArrayList<Student>();
    Placement placement = null;
    Hashtable<Integer, ArrayList<Student>> placed = new Hashtable<Integer, ArrayList<Student>>();
    UserTransaction tx = null;
    @Resource
    EJBContext context;

    public StudentPlacementBL() {
        students = new ArrayList<Student>();
        // tx = context.getUserTransaction();
    }

    public List<Student> getAboveCutpointStudents() {
        return this.aboveCutPointStudents;
    }

    public List<Student> getBelowCutpointStudents() {
        return this.belowCutPointStudents;
    }

    @Override
    public List<Student> getStudents(int placementId) {
        try {
            tx = context.getUserTransaction();
            tx.begin();
            Placement p = em.find(Placement.class, placementId);
            if (p == null) {
                return null;
            }
            placement = p;
            students = em.createQuery("SELECT p FROM " + Student.class.getName() + " p WHERE p.placementId=:placementId").setParameter("placementId", p).getResultList();
            sortByCGPA(students);
            tx.commit();
            return students;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> placeOncePreview(int placementId) {
        try {
            Placement p = em.find(Placement.class, placementId);
            if (p == null) {
                return null;
            }
            placement = p;
            students = em.createQuery("SELECT p FROM " + Student.class.getName() + " p WHERE p.placementId=:placementId").setParameter("placementId", p).getResultList();
            this.unplaceAll();
            tx.begin();
            sortByCGPA(students);
            processPlacement();
            List<Student> studentsplaced = new ArrayList<Student>();
            for (DepartmentPlacement dp : placement.getDepartmentPlacementList()) {
                List<Student> studs = placed.get(dp.getId());
                if (studs != null) {
                    studentsplaced.addAll(studs);
                }
            }
            tx.commit();
            students = studentsplaced;
            return studentsplaced;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public List<Student> placeOnceSave() {
        try {
            tx = context.getUserTransaction();
            tx.begin();
            int i = 0;
            for (Student s : students) {
                if (s.getDepartmentId() == null) {
                    System.out.println("department is null while saving");
                }
                System.out.println(i++ + ". " + s.getDepartmentId().getCode());
                
                em.merge(s);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tx.rollback();
                return new ArrayList<Student>();
            } catch (IllegalStateException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;

    }

    @Override
    public List<Student> unplaceAll() {
        try {
            students = this.getStudents(placement.getId());
            tx.begin();
            if (students != null) {
                for (Student s : students) {
                    s.setDepartmentId(null);
                    s.setPlacementReason(null);
                    em.merge(s);
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(StudentPlacementBL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }

    private void processPlacement() {
        if (placement == null || students == null) {
            return;
        }
        sortByCGPA(students);
        //List<Student> allStudents = new ArrayList<Student>();
        //allStudents.addAll(students);
        Collections.reverse(students);
        List<Student> disabilityStudents = this.getDisabilityStudents(students);
        // allStudents.removeAll(disabilityStudents);
        List<Student> aboveCutPoint = this.getAboveCutPoint(students);
        aboveCutPointStudents.addAll(aboveCutPoint);
        //allStudents.removeAll(aboveCutPoint);
        List<Student> femaleStudents = this.getFemaleStudents(students);
        // allStudents.removeAll(femaleStudents);
        List<Student> regionStudents = this.getRegionStudents(students);
        //allStudents.removeAll(regionStudents);
        List<Student> otherStudents = new ArrayList<Student>();
        //otherStudents.addAll(allStudents);
        List<DepartmentPlacement> deptPlacements = placement.getDepartmentPlacementList();

        for (int i = 1; i <= deptPlacements.size(); i++) {
            for (DepartmentPlacement dp : deptPlacements) {
                checkAndCreateDictionary(placed, dp.getId());
                //above cut point placement for this department
                System.out.print(dp.getDepartmentId().getCode() + " capacity :" + dp.getCapacity() + " Choice:" + i);
                List<Student> aboveCutPointList = this.getStudentsWithChoice(aboveCutPoint, dp.getId(), i);
                int remaining = dp.getCapacity() - placed.get(dp.getId()).size();
                System.out.println(" remains: " + remaining);
                if (remaining > 0) {
                    //disability placement
                    List<Student> disabilityList = this.getStudentsWithChoice(disabilityStudents, dp.getId(), i);
                    List<Student> placeddis = placeByCotta(disabilityList, placed.get(dp.getId()), dp, remaining, "Physicaly Challenged Cotta, choice " + i);
                    disabilityStudents.removeAll(placeddis);
                    System.out.println(" disability: " + placeddis.size());
                    //above cutpoint placement
                    double topPercentage = placement.getRuleId().getTopPercentage() / 100.0;
                    int cgpaCotta = (int) ((remaining * topPercentage)+0.5);
                    for(Student s: placeddis){
                        if(aboveCutPointList.contains(s)){
                            aboveCutPointList.remove(s);
                        }
                    }
                    List<Student> placedabove = placeByCotta(aboveCutPointList, placed.get(dp.getId()), dp, cgpaCotta, "Above " + placement.getRuleId().getCutPoint() + " CGPA Quota, choice " + i);
                    aboveCutPoint.removeAll(placedabove);
                    System.out.println(" top cotta:" + topPercentage + " x " + remaining + " = " + cgpaCotta);
                    System.out.println(" Above: " + placedabove.size());

                    //female students placement
                    double femalePercentage = placement.getRuleId().getFemalePercentage() / 100.0;
                    int femateCotta = (int) (remaining * femalePercentage);
                    List<Student> femaleList = this.getStudentsWithChoice(femaleStudents, dp.getId(), i);
                    femaleList.addAll(this.getNonPlacedFemaleStudents(aboveCutPointList));
                    List<Student> placedfemales = placeByCotta(femaleList, placed.get(dp.getId()), dp, femateCotta, "Female Quota, choice " + i);
                    femaleStudents.removeAll(placedfemales);
                    System.out.println(" female cotta:" + femalePercentage + " x " + remaining + " = " + femateCotta);
                    System.out.println(" female: " + placedfemales.size());
                    //region placement
                    double regionpercentage = placement.getRuleId().getRegionPercentage() / 100.0;
                    int regionCotta = (int) (remaining * regionpercentage);
                    List<Student> regionStudList = this.getStudentsWithChoice(regionStudents, dp.getId(), i);
                    List<Student> regAbovStu = this.getNonPlacedRegionStudents(aboveCutPointList);
                    List<Student> regFemStu = this.getNonPlacedRegionStudents(femaleList);
                    for(Student s: regAbovStu){
                        if(!placedfemales.contains(s)){
                           regionStudList.add(s);
                        }                        
                    }
                    regionStudList.addAll(regFemStu);
                    List<Student> placedregion = placeByCotta(regionStudList, placed.get(dp.getId()), dp, regionCotta, "Region Quota, choice " + i);
                    regionStudents.removeAll(placedregion);
                    System.out.println(" region cotta:" + regionpercentage + " x " + remaining + " = " + regionCotta);
                    System.out.println(" region: " + placedregion.size());
                }
            }
        }
        otherStudents.addAll(aboveCutPoint);
        otherStudents.addAll(femaleStudents);
        otherStudents.addAll(regionStudents);
        System.err.println(" Remaining: " + otherStudents.size());
        this.belowCutPointStudents.addAll(otherStudents);
    }

    private List<Student> getNonPlacedStudents(List<Student> students) {

        List<Student> st = new ArrayList<Student>();
        for (Student s : students) {
            if (s.getDepartmentId() == null) {
                st.add(s);
            }
        }
        return st;
    }

    private List<Student> placeByCotta(List<Student> listOfStudents, List<Student> allPlaced, DepartmentPlacement dp, int cotta, String reason) {
        List<Student> placedStudents = new ArrayList<Student>();

        //<editor-fold defaultstate="collapsed" desc="if less than cotta">
        if (listOfStudents.size() <= cotta) {
            for (Student s : listOfStudents) {
                s.setDepartmentId(dp.getDepartmentId());
                s.setPlacementReason(reason);
                allPlaced.add(s); // add to placed students list
            }

            return listOfStudents;

        } //</editor-fold>
        else {
            List<Student> tobePlaceds = listOfStudents.subList(0, cotta);
            // place tobePlaceds            
            for (int a = 0; a < tobePlaceds.size(); a++) {
                Student s = tobePlaceds.get(a);
                s.setDepartmentId(dp.getDepartmentId());
                s.setPlacementReason(reason);
                allPlaced.add(s); // add to placed students list
            }
            if (tobePlaceds.size() > 0) {
                placedStudents.addAll(tobePlaceds);
                // student who have equal cgpa with the last student in the cotta
                for (int k = tobePlaceds.size(); k < listOfStudents.size(); k++) {   //??????should check back in the list??????
                    Student vc = tobePlaceds.get(tobePlaceds.size() - 1);
                    Student s = listOfStudents.get(k);
                    if (vc.getCgpa() == s.getCgpa()) {
                        s.setDepartmentId(dp.getDepartmentId());
                        s.setPlacementReason(reason + " violeted");
                        allPlaced.add(s); // add to placed students list
                        placedStudents.add(s);
                    } else {
                        break;
                    }

                }
            }
        }
        return placedStudents;
    }

    /*
     * returns a list of students choice who choose the specified program as
     * rank of rank
     */
    private ArrayList<Student> getStudentsWithChoice(List<Student> students, int pid, int rank) {

        try {
            ArrayList<Student> st = new ArrayList<Student>();

            for (Student s : students) {
                if (s.getDepartmentId() != null || s.getChoiceList() == null) {
                    continue;
                }
                List<Choice> choices = s.getChoiceList();
                for (Choice c : choices) {
                    if (c.getDepartmentPlacementId().getId() == pid && c.getRank() == rank) {
                        st.add(s);
                    }
                }
            }
            return st;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkAndCreateDictionary(Hashtable<Integer, ArrayList<Student>> placed, int j) {
        try {
            if (!placed.containsKey(j)) {
                placed.put(j, new ArrayList<Student>());
            }
        } catch (Exception e) {
            if (placed != null) {
                placed.put(j, new ArrayList<Student>());
            }
        }
    }

    private List<Student> getAboveCutPoint(final List<Student> students) {
        try {
            List<Student> aboveStudents = new ArrayList<Student>();
            double cutpoint = placement.getRuleId().getCutPoint();
            for (Student student : students) {
                if (student.getCgpa() >= cutpoint) {
                    aboveStudents.add(student);
                }
            }
            return aboveStudents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> getDisabilityStudents(List<Student> students) {
        try {
            List<Student> disabilityStudents = new ArrayList<Student>();
            for (Student student : students) {
                if (student.getDisability() == null || !student.getDisability().equalsIgnoreCase("NONE")) {
                    disabilityStudents.add(student);
                }
            }
            return disabilityStudents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> getFemaleStudents(final List<Student> students) {
        try {
            List<Student> femaleStudents = new ArrayList<Student>();
            for (Student student : students) {
                if (student.getGender() != null && student.getGender().equalsIgnoreCase("F")) {
                    femaleStudents.add(student);
                }
            }
            return femaleStudents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> getNonPlacedFemaleStudents(final List<Student> students) {
        try {
            List<Student> femaleStudents = new ArrayList<Student>();
            for (Student student : students) {
                if (student.getDepartmentId() == null && student.getGender() != null && student.getGender().equalsIgnoreCase("F")) {
                    femaleStudents.add(student);
                }
            }
            return femaleStudents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> getRegionStudents(final List<Student> students) {
        try {
            List<Student> regionStudents = new ArrayList<Student>();
            for (Student s : students) {
                if (s.getRegion().equalsIgnoreCase(Region.SOMALI) || s.getRegion().equalsIgnoreCase(Region.GAMBELLA)
                        || s.getRegion().equalsIgnoreCase(Region.BENSHANGUL) || s.getRegion().equalsIgnoreCase(Region.AFAR)) {
                    regionStudents.add(s);
                }
            }
            return regionStudents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> getNonPlacedRegionStudents(final List<Student> students) {
        try {
            List<Student> regionStudents = new ArrayList<Student>();
            for (Student s : students) {
                if (s.getDepartmentId() == null && s.getRegion().equalsIgnoreCase(Region.SOMALI) || s.getRegion().equalsIgnoreCase(Region.GAMBELLA)
                        || s.getRegion().equalsIgnoreCase(Region.BENSHANGUL) || s.getRegion().equalsIgnoreCase(Region.AFAR)) {
                    regionStudents.add(s);
                }
            }
            return regionStudents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * sorts a list of students by CGPA and access choices to fetch lazzily
     */
    private void sortByCGPA(List<Student> student) {

        Collections.sort(student, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                List<Choice> ch = o1.getChoiceList();
                List<Choice> chs = o2.getChoiceList();
                if (ch != null) {
                    o1.setChoiceList(ch);
                    System.out.println(o1.getChoiceList().size());
                }
                if (chs != null) {
                    o2.setChoiceList(chs);
                    System.out.println(o2.getChoiceList().size());
                }
                if (o1.getCgpa() < o2.getCgpa()) {
                    return -1;
                } else if (o1.getCgpa() > o2.getCgpa()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }
}
