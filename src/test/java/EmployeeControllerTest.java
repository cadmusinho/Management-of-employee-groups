import com.backend.controllers.EmployeeController;
import com.backend.models.Employee;
import com.backend.models.EmployeeCondition;
import com.backend.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Test
    public void addEmployee_shouldReturnCreated() {
        Employee employee = new Employee("Jan", "Kowalski", EmployeeCondition.PRESENT, 1990, 4500.0);
        ResponseEntity<Employee> response = employeeController.addEmployee(employee);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    public void deleteEmployee_shouldReturnNoContent() {
        when(employeeService.deleteEmployee(1L)).thenReturn(true);
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void exportAllEmployeesToCSV_shouldReturnCSVFile() {
        byte[] csvContent = "id,name,surname\n1,Jan,Kowalski\n2,Anna,Nowak\n".getBytes();
        when(employeeService.exportEmployees2CSV()).thenReturn(new ByteArrayInputStream(csvContent));
        ResponseEntity<byte[]> response = employeeController.exportEmployees2CSV();
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertArrayEquals(csvContent, response.getBody());
    }
}
