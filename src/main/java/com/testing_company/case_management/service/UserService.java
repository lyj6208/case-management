package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.TeamResponseDTO;
import com.testing_company.case_management.dto.UserResponseDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Department;
import com.testing_company.case_management.model.JobLevel;
import com.testing_company.case_management.model.Team;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.repository.DepartmentRepository;
import com.testing_company.case_management.repository.JobLevelRepository;
import com.testing_company.case_management.repository.TeamRepository;
import com.testing_company.case_management.repository.UserRepository;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import static com.testing_company.case_management.util.BeanUtil.findIfIdPresent;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JobLevelRepository jobLevelRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;

    public User createUser(User user){
        LogUtils.logRequest(log,this,"建立User：{}",user);

        int TaiwanYear=user.getHiredAt().getYear()-1911;

        String prefix=String.format("%03d",TaiwanYear);

        String latestNumber=userRepository.findMaxEmployeeNumberByPrefix(prefix+"%");

        int nextNumber=1;
        if(latestNumber!=null){
            String lastDigits=latestNumber.substring(3);
            nextNumber=Integer.parseInt(lastDigits)+1;
        }
        String employeeNumber=prefix+String.format("%04d",nextNumber);
        user.setEmployeeNumber(employeeNumber);
        User createdUser=userRepository.save(user);
        LogUtils.logResponse(log,this,"建立User：{}",user);
        return createdUser;
    }
    public UserResponseDTO findUserById(Long userId){
        LogUtils.logRequest(log,this,"尋找User_ID：{}",userId);
        User foundUser=userRepository.findById(userId).orElseThrow(()->new NotFoundException("找不到ID為"+userId+"之User"));
        UserResponseDTO response=convertToDTO(foundUser);
        LogUtils.logResponse(log,this,"尋找User_ID：{}",userId);
        return response;
    }

    public UserResponseDTO convertToDTO(User user){

        JobLevel jobLevel=findIfIdPresent(user.getJobLevelId(),jobLevelRepository::findById);
        Team team=findIfIdPresent(user.getTeamId(),teamRepository::findById);
        Department department=findIfIdPresent(team.getDepartmentId(),departmentRepository::findById);
        User modifier=findIfIdPresent(user.getLastModifiedById(),userRepository::findById);

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .sex(user.getSex())
                .idNumber(user.getIdNumber())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .emailPrivate(user.getEmailPrivate())
                .emailCompany(user.getEmailCompany())
                .employeeNumber(user.getEmployeeNumber())
                .jobLevel(jobLevel!=null?jobLevel.getJobLevel():null)
                .department(department!=null?department.getDepartment():null)
                .team(team!=null?team.getTeam():null)
                .systemRole(user.getSystemRole())
                .hiredAt(user.getHiredAt())
                .createdTime(user.getCreatedTime())
                .lastModifiedTime(user.getLastModifiedTime())
                .lastModifiedBy(modifier!=null?modifier.getEmployeeNumber()+"_"+modifier.getName():null)
                .build();
    }

    public Page<UserResponseDTO>findAllUsers(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有User");
        Page<User>foundUsers=userRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有User");
        return foundUsers.map(this::convertToDTO);
    }
    public UserResponseDTO updateUserById(Long userId, User inputtedUser){
        LogUtils.logRequest(log,this,"更新User_ID：{}",userId);
        User updatedUser=userRepository.findById(userId).orElseThrow(()->new NotFoundException("找不到ID為"+userId+"之User"));
        BeanUtil.copyNotNullProperties(inputtedUser,updatedUser);
        userRepository.save(updatedUser);
        LogUtils.logResponse(log,this,"更新User_ID：{}",userId);
        return convertToDTO(updatedUser);
    }
}
