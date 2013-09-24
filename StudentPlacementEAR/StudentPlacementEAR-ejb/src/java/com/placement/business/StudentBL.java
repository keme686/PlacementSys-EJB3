/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Choice;
import com.placement.entity.Department;
import com.placement.entity.Placement;
import com.placement.entity.Student;
import com.placement.entity.UserField;
import com.placement.entity.Users;
import com.placement.exception.PlacementAppException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kemele
 */
@Stateless
@Remote(StudentBLRemote.class)
public class StudentBL implements StudentBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;

    @Override
    public boolean save(Student student) throws PlacementAppException {
        try {
            if (student != null) {
                Users user = new Users(student.getIdNumber());
                user.setCreatedDate(new Date());
                user.setLastModified(new Date());
                user.setRole(UserField.STUDENT);
                user.setStatus(UserField.STATUS_DEACTIVATED);
                user.setPassword(md5(student.getIdNumber()));
                //student.setUsername(user);
                em.persist(student);
                em.persist(user);
                return true;
            } else {
                throw new PlacementAppException("Invalid student information. Student object should not be null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Student student) throws PlacementAppException {
        try {
            if (student != null) {
                em.merge(student);
                return true;
            } else {
                throw new PlacementAppException("Invalid student information. Student object should not be null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String idnum) {
        try {
            if (idnum != null) {
                Student s = em.find(Student.class, idnum);
                em.remove(s);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student get(String idnum) {
        try {
            if (idnum != null) {
                Student s = em.find(Student.class, idnum);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        try {
            String q = "SELECT p FROM " + Student.class.getName() + " p";
            Query query = em.createQuery(q);
            List<Student> Students = query.getResultList();
            return Students;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int saveAll(List<Student> cellDataList) {
        int i = 0;
        try {
            if (cellDataList != null) {
                for (Student student : cellDataList) {
                    for (Choice c : student.getChoiceList()) {
                        c.setIdNumber(student);
                    }
                    Users user = new Users(student.getIdNumber());
                    user.setCreatedDate(new Date());
                    user.setLastModified(new Date());
                    user.setRole(UserField.STUDENT);
                    user.setStatus(UserField.STATUS_DEACTIVATED);
                    user.setPassword(md5(student.getIdNumber()));

                    //student.setUsername(user);
                    em.persist(student);
                    em.persist(user);
                    i++;
                    //else throw new PlacementAppException("Invalid student information. Student object should not be null!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return i;
    }

    @Override
    public List<Student> search(Student student, int cgpabound) {
        try {
            Department d = student.getDepartmentId();
            Placement p = student.getPlacementId();
            String gender = student.getGender();
            String disab = student.getDisability();
            String reg = student.getRegion();
            Double cgpa = student.getCgpa();
            String q = "SELECT p FROM " + Student.class.getName() + " p WHERE p.placementId=:placementId ";
            if (d != null) {
                q += "AND p.departmentId=:departmentId ";
            }
            if (gender != null && !gender.trim().equals("")) {
                q += "AND p.gender=:gender ";
            }
            if (disab != null && !disab.trim().equals("") && !disab.trim().equals("0")) {
                q += "AND p.disability=:disab ";
            }
            if (reg != null && !reg.trim().equals("") && !reg.trim().equals("0")) {
                q += "AND p.region=:region ";
            }
            if (cgpa != null && cgpa > 0.0) {
                switch (cgpabound) {
                    case 0:
                        q += "AND p.cgpa = :cgpa ";
                        break;
                    case 1:
                        q += "AND p.cgpa > :cgpa ";
                        break;
                    case 2:
                        q += "AND p.cgpa >= :cgpa ";
                        break;
                    case 3:
                        q += "AND p.cgpa < :cgpa ";
                        break;
                    case 4:
                        q += "AND p.cgpa <= :cgpa ";
                        break;
                    case 5:
                        q += "AND p.cgpa <> :cgpa ";
                        break;
                }
            }
            Query query = em.createQuery(q).setParameter("placementId", p);
            if (d != null) {
                query.setParameter("departmentId", d);
            }
            if (gender != null && !gender.trim().equals("")) {
                query.setParameter("gender", gender);
            }
            if (disab != null && !disab.trim().equals("") && !disab.trim().equals("0")) {
                query.setParameter("disab", disab);
            }
            if (reg != null && !reg.trim().equals("") && !reg.trim().equals("0")) {
                query.setParameter("region", reg);
            }
            if (cgpa != null && cgpa > 0.0) {
                query.setParameter("cgpa", cgpa);
            }
            List<Student> Students = query.getResultList();
            return Students;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> search(String idnum) {
        try {
            String q = "SELECT p FROM " + Student.class.getName() + " p WHERE p.idNumber like :idnum";
            Query query = em.createQuery(q).setParameter("idnum", "%" + idnum + "%");
            List<Student> Students = query.getResultList();
            return Students;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Choice> getChoices(String idnum) {
        try {
            Student s = this.get(idnum);
            String q = "SELECT p FROM " + Choice.class.getName() + " p WHERE p.idNumber =:idNumber AND p.placementId=:placementId";
            Query query = em.createQuery(q).setParameter("idNumber", s).setParameter("placementId", s.getPlacementId());
            List<Choice> choices = query.getResultList();
            return choices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Choice>();
    }

    public String md5(String password) {
        String md5 = null;
        if (password == null) {
            return null;
        }
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(password.getBytes(), 0, password.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
