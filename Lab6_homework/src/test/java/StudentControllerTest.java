import com.example.demo.RedirectBean;
import com.example.demo.controller.StudentController;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.demo.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentController studentController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        // Arrange
        List<Student> students = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<Student> result = studentController.getAllStudents();

        // Assert
        assertEquals(students, result);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long studentId = 1L;

        // Act
        studentController.deleteById(studentId);

        // Assert
        verify(studentRepository, times(1)).deleteById(studentId);
    }

}