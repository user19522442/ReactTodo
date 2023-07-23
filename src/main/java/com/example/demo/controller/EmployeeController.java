package com.example.demo.controller;

import com.example.demo.exception.ResouceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1/employees")
public class EmployeeController {
//    create a variable of Repository to deal with data
    @Autowired
    private EmployeeRepository employeeRepository;

//    get all the employees
    @GetMapping
    public List<Employee> getAllEmployees(){
        return  employeeRepository.findAll();
    }

//    add employee to database
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return  employeeRepository.save(employee);
    }

//    get the employee by id
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("Employee not exist with id: "+ id));
        return ResponseEntity.ok(employee);
    }

//    update employee by id
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("Employee not exist with id: "+id));
        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

//    delete employee by id
    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable long id){
        Employee deleteEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("Employee not exist with id: "+id));
        employeeRepository.delete(deleteEmployee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
