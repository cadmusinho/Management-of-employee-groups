import com.backend.controllers.ClassEmployeeController;
import com.backend.models.ClassEmployee;
import com.backend.models.Employee;
import com.backend.services.ClassEmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClassEmployeeControllerTest {

    @InjectMocks
    private ClassEmployeeController classEmployeeController;

    @Mock
    private ClassEmployeeService classEmployeeService;

    @Test
    public void getAllGroups_shouldReturnListOfGroups() throws Exception {
        List<ClassEmployee> groups = Arrays.asList(
                new ClassEmployee("Grupa A", 10),
                new ClassEmployee("Grupa B", 15)
        );

        when(classEmployeeService.getAllGroups()).thenReturn(groups);

        ResponseEntity<List<ClassEmployee>> response = classEmployeeController.getAllGroups();
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void addGroup_shouldReturnCreated() throws Exception {
        ClassEmployee group = new ClassEmployee("Grupa C", 20);
        ResponseEntity<ClassEmployee> response = classEmployeeController.addGroup(group);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    public void deleteGroup_shouldReturnNoContent() {
        when(classEmployeeService.deleteGroup(1L)).thenReturn(true);
        ResponseEntity<Void> response = classEmployeeController.deleteGroup(1L);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void getAllEmployeesInGroup_shouldReturnListOfEmployees() {
        List<Employee> employees = Arrays.asList(
                new Employee("Jan", "Kowalski", null, 1990, 4500.0),
                new Employee("Anna", "Nowak", null, 1995, 5000.0)
        );

        when(classEmployeeService.getAllEmployeesInGroup(1L)).thenReturn(employees);

        ResponseEntity<List<Employee>> response = classEmployeeController.getAllEmployeesInGroup(1L);
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getGroupFillPercentage_shouldReturnPercentage() {
        when(classEmployeeService.getGroupCurrentSize(1L)).thenReturn(85.25);
        ResponseEntity<Double> response = classEmployeeController.getGroupCurrentSize(1L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(85.25, response.getBody());
    }
}
