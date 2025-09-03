package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.requestDTO.RegisterRequestDTO;
import com.testing_company.case_management.dto.responseDTO.UserResponseDTO;
import com.testing_company.case_management.enums.Role;
import com.testing_company.case_management.exception.DuplicateException;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.testing_company.case_management.util.BeanUtil.findIfIdPresent;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JobLevelRepository jobLevelRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    public UserResponseDTO createUser(RegisterRequestDTO registerRequestDTO){
        LogUtils.logRequest(log,this,"建立User：{}",registerRequestDTO);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentUsername=authentication.getName();
        User currentUser=userRepository.findByUsername(currentUsername).orElseThrow(()->new NotFoundException("找不到使用者"));

        int TaiwanYear=registerRequestDTO.getHiredAt().getYear()-1911;

        String prefix=String.format("%03d",TaiwanYear);

        String latestNumber=userRepository.findMaxEmployeeNumberByPrefix(prefix+"%");

        int nextNumber=1;
        if(latestNumber!=null){
            String lastDigits=latestNumber.substring(3);
            nextNumber=Integer.parseInt(lastDigits)+1;
        }
        if(userRepository.existsByIdNumber(registerRequestDTO.getIdNumber())){
            throw new DuplicateException("身分證字號已存在資料庫，請確認資料是否重複建立");
        }

        String employeeNumber=prefix+String.format("%04d",nextNumber);
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User userToSave=mapper.map(registerRequestDTO, User.class);

        userToSave.setEmailCompany(employeeNumber+"@company.com");
        userToSave.setEmployeeNumber(employeeNumber);
        userToSave.setUsername(employeeNumber);
        userToSave.setPassword(passwordEncoder.encode(registerRequestDTO.getIdNumber().substring(8,10)));
        if(currentUser!=null){userToSave.setLastModifiedById(currentUser.getId());};

        userRepository.save(userToSave);
        LogUtils.logResponse(log,this,"建立User：{}",registerRequestDTO);
        return convertToDTO(userToSave);
    }
    public List<UserResponseDTO> createUsers(List<RegisterRequestDTO> registerRequestDTO){
        LogUtils.logRequest(log,this,"建立User：{}",registerRequestDTO);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentUsername=authentication.getName();
        User currentUser=userRepository.findByUsername(currentUsername).orElseThrow(()->new NotFoundException("找不到使用者"));
        List<User>createdUsers=new ArrayList<>();
        for(RegisterRequestDTO r:registerRequestDTO){
            int TaiwanYear=r.getHiredAt().getYear()-1911;

            String prefix=String.format("%03d",TaiwanYear);

            String latestNumber=userRepository.findMaxEmployeeNumberByPrefix(prefix+"%");

            int nextNumber=1;
            if(latestNumber!=null){
                String lastDigits=latestNumber.substring(3);
                nextNumber=Integer.parseInt(lastDigits)+1;
            }
            if(userRepository.existsByIdNumber(r.getIdNumber())){
                throw new DuplicateException("身分證字號已存在資料庫，請確認資料是否重複建立");
            }

            String employeeNumber=prefix+String.format("%04d",nextNumber);
            User createdUser=new User();
            ModelMapper mapper=new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            mapper.map(r,User.class);

            createdUser.setEmailCompany(employeeNumber+"@company.com");
            createdUser.setEmployeeNumber(employeeNumber);
            createdUser.setUsername(employeeNumber);
            createdUser.setPassword(passwordEncoder.encode(r.getIdNumber().substring(8,10)));
            if(currentUser!=null){createdUser.setLastModifiedById(currentUser.getId());};

            userRepository.save(createdUser);
            createdUsers.add(createdUser);
        }

        LogUtils.logResponse(log,this,"建立User：{}",registerRequestDTO);
        return createdUsers.stream().map(this::convertToDTO).toList();
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
                .jobLevel(jobLevel!=null?jobLevel.getJobLevelNameInChinese():null)
                .department(department!=null?department.getDepartmentNameInChinese():null)
                .team(team!=null?team.getTeamNameInChinese():null)
                .role(user.getRole())
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
