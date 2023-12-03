package com.example.demo.ejb;

import com.example.demo.model.Project;
import com.example.demo.model.Student;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProjectAssignmentBean {
    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;

    @EJB
    private ProjectAvailabilityBean projectAvailabilityBean;

    public void assignProjectToStudent(Long projectId, Long studentId) {
        if (projectAvailabilityBean.isProjectAvailable(projectId)) {
            Project project = entityManager.find(Project.class, projectId);
            Student student = entityManager.find(Student.class, studentId);
            if (project != null && student != null) {
                project.assignStudent(student);
                entityManager.merge(project);
            }
        }
    }

    public void deleteAssignment(Long projectId) {
        Project project = entityManager.find(Project.class, projectId);
        if(project != null) {
            project.assignStudent(null);
            entityManager.merge(project);
        }
    }
}
