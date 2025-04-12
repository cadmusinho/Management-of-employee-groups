package com.backend.controllers;

import com.backend.models.ClassEmployee;
import com.backend.models.Employee;
import com.backend.services.ClassEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/groups")
public class ClassEmployeeController {
    @Autowired
    private ClassEmployeeService classEmployeeService;

    @GetMapping
    public ResponseEntity<List<ClassEmployee>> getAllGroups(){
        try {
            List<ClassEmployee> groups = classEmployeeService.getAllGroups();
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ClassEmployee> addGroup(@RequestBody ClassEmployee classEmployee){
        try {
            classEmployeeService.addGroup(classEmployee);
            return new ResponseEntity<>(classEmployee, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id){
        try {
            boolean deleted = classEmployeeService.deleteGroup(id);
            if(deleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/fill")
    public ResponseEntity<Double> getGroupCurrentSize(@PathVariable("id") Long id){
        try {
            Double percentage = classEmployeeService.getGroupCurrentSize(id);
            if(percentage == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(percentage, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/employee")
    public ResponseEntity<List<Employee>> getAllEmployeesInGroup(@PathVariable("id") Long id){
        try {
            List<Employee> employees = classEmployeeService.getAllEmployeesInGroup(id);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
