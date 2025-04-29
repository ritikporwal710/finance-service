package com.financetrack.development.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.Repository.SalaryRepository;
import com.financetrack.development.Repository.UserRepository;
import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.model.Salary;
import com.financetrack.development.model.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class SalaryController {
    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add-salary")
    public ResponseEntity<ApiResponse<Salary>> addSalary(@RequestBody Salary data,HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));

        // System.out.println("data in controller: " + data);

        User user = userRepository.findById(userId).get();

        data.setUser(user);

        // System.out.println("user in controller: " + user);

        Salary isPresent = salaryRepository.findByUser_IdAndMonth(userId, data.getMonth());
        System.out.println("isPresent in controller: " + isPresent);
        if(isPresent == null){
            Salary salary = salaryRepository.save(data);
            ApiResponse<Salary> entity = new ApiResponse<>(true, "Salary Added Successfully", salary);
            return ResponseEntity.status(200).body(entity);
        }


        Salary salary = salaryRepository.findByUser_IdAndMonth(userId, data.getMonth());

        System.out.println("salary in controller: " + salary);

        salary.setSalary(data.getSalary());

        salaryRepository.save(salary);

        ApiResponse<Salary> entity = new ApiResponse<>(true, "Salary Updated Successfully", salary);
        return ResponseEntity.status(200).body(entity);

    }


    @GetMapping("/get-all-salaries")
    public ResponseEntity<ApiResponse<List<Salary>>> getMethodName(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        List<Salary> salaries = salaryRepository.findAllByUser_Id(userId);

        ApiResponse<List<Salary>> entity = new ApiResponse<>(true, "Salary Fetched Successfully", salaries);
        return ResponseEntity.status(200).body(entity);
    }
    


    
}
