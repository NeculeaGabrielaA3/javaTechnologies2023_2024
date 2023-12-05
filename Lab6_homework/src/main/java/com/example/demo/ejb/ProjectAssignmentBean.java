package com.example.demo.ejb;

import com.example.demo.RedirectBean;
import com.example.demo.model.Project;
import com.example.demo.model.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.*;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateful
@Data
@Named
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProjectAssignmentBean {

    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;

    @EJB
    private ProjectAvailabilityBean projectAvailabilityBean;

    @EJB
    private AssignmentTrackingBean assignmentTrackingBean;

    private List<Long> projectsId;
    private Long studentId;

    @Remove
    public void save()
    {
        for(Long id: projectsId) {
            Project project = entityManager.find(Project.class, id);
            if (project != null && projectAvailabilityBean.isProjectAvailable(project)) {
                Student student = entityManager.find(Student.class, studentId);
                if (student != null) {
                    project.assignStudent(student);
                    assignmentTrackingBean.addAssignment(project.getId(), studentId);
                    entityManager.merge(project);
                }
            }
        }
        RedirectBean.redirectToViewAssignedProjectsPage();
    }

}
