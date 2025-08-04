package com.testing_company.case_management.service;

import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Department;
import com.testing_company.case_management.repository.DepartmentRepository;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class DepartmentService {
    public final DepartmentRepository departmentRepository;

    public Department createDepartment(Department department){
        LogUtils.logRequest(log,this,"建立Department"+department);
        Department createdDepartment=departmentRepository.save(department);
        LogUtils.logResponse(log,this,"建立Department"+department);
        return createdDepartment;
    }

    public Department findDepartmentById(Long departmentId){
        LogUtils.logRequest(log,this,"尋找Department_ID"+departmentId);
        Department foundDepartment=departmentRepository.findById(departmentId).orElseThrow(()->new NotFoundException("找不到ID為"+departmentId+"之Department"));
        LogUtils.logResponse(log,this,"尋找Department_ID"+departmentId);
        return foundDepartment;
    }
    public Page<Department> findAllDepartments(Pageable pageable){
        LogUtils.logResponse(log,this,"尋找所有Department");
        Page<Department> foundAllDepartments=departmentRepository.findAll(pageable);
        LogUtils.logRequest(log,this,"尋找所有Department");
        return foundAllDepartments;
    }
    public Department updateDepartmentById(Long departmentId, Department inputtedDepartment){
        LogUtils.logRequest(log,this,"更新Department_ID"+departmentId);
        Department updatedDepartment=departmentRepository.findById(departmentId).orElseThrow(()->new NotFoundException("找不到department ID："+departmentId));
        BeanUtil.copyNotNullProperties(inputtedDepartment,updatedDepartment);
        departmentRepository.save(updatedDepartment);
        LogUtils.logResponse(log,this,"更新Department_ID"+departmentId);
        return updatedDepartment;
    }
}
