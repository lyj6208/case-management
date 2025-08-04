package com.testing_company.case_management.controller;

import com.testing_company.case_management.model.Department;
import com.testing_company.case_management.service.DepartmentService;
import com.testing_company.case_management.util.LogUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/department")
@AllArgsConstructor
public class DepartmentController {
    public final DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<Department>createDepartment(@Valid @RequestBody Department department){
        LogUtils.logRequest(log,this,"建立Department"+department);
        Department createdDepartment=departmentService.createDepartment(department);
        LogUtils.logResponse(log,this,"建立Department"+department);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @GetMapping("/findById/{departmentId}")
    public ResponseEntity<Department>findDepartmentById(@PathVariable Long departmentId){
        LogUtils.logRequest(log,this,"尋找Department_ID"+departmentId);
        Department foundDepartment=departmentService.findDepartmentById(departmentId);
        LogUtils.logResponse(log,this,"尋找Department_ID"+departmentId);
        return ResponseEntity.ok(foundDepartment);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<Department>>findAllDepartments(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "id") String sortBy,
            @RequestParam (defaultValue = "desc") String sortDir
    ){
        LogUtils.logResponse(log,this,"尋找所有Department");
        Sort sort=sortDir.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<Department>foundAllDepartments=departmentService.findAllDepartments(pageable);
        LogUtils.logRequest(log,this,"尋找所有Department");
        return ResponseEntity.ok(foundAllDepartments);
    }
    @PostMapping("/update/{departmentId}")
    public ResponseEntity<Department>updateDepartmentById(
            @PathVariable Long departmentId,
            @RequestBody Department department
    ){
        LogUtils.logRequest(log,this,"更新Department_ID"+departmentId);
        Department updatedDepartment=departmentService.updateDepartmentById(departmentId, department);
        LogUtils.logResponse(log,this,"更新Department_ID"+departmentId);
        return ResponseEntity.ok(updatedDepartment);
    }

}
