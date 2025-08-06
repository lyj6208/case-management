package com.testing_company.case_management.service;

import com.testing_company.case_management.dto.TeamResponseDTO;
import com.testing_company.case_management.exception.NotFoundException;
import com.testing_company.case_management.model.Department;
import com.testing_company.case_management.model.JobLevel;
import com.testing_company.case_management.model.Team;
import com.testing_company.case_management.repository.DepartmentRepository;
import com.testing_company.case_management.repository.TeamRepository;
import com.testing_company.case_management.util.BeanUtil;
import com.testing_company.case_management.util.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    public List<Team> createTeam(List<Team> team){
        LogUtils.logRequest(log,this,"建立Team：{}"+team);
        List<Team> createdTeam=teamRepository.saveAll(team);
        LogUtils.logResponse(log,this,"建立Team：{}"+team);
        return createdTeam;
    }

    public TeamResponseDTO findTeamById(Long teamId){
        LogUtils.logRequest(log,this,"尋找Team_ID：{}"+teamId);
        Team foundTeam=teamRepository.findById(teamId).orElseThrow(()->new NotFoundException("找不到ID為"+teamId+"之Team"));
        Department department=departmentRepository.findById(foundTeam.getDepartmentId()).orElse(null);
        TeamResponseDTO teamResponseDTO=new TeamResponseDTO(department.getDepartment(), foundTeam.getTeam());
        LogUtils.logResponse(log,this,"尋找Team_ID：{}"+teamId);
        return teamResponseDTO;
    }

    public Page<TeamResponseDTO> findAllTeams(Pageable pageable){
        LogUtils.logRequest(log,this,"尋找所有Team");
        Page<Team>foundTeam=teamRepository.findAll(pageable);
        LogUtils.logResponse(log,this,"尋找所有Team");
        return foundTeam.map(team -> {
            String departmentName = departmentRepository.findById(team.getDepartmentId())
                    .map(Department::getDepartment)
                    .orElse("未知部門"); // 可自訂 fallback 字串
            return new TeamResponseDTO(departmentName, team.getTeam());
        });
    }
    public Team updateTeamById(Long teamId, Team inputtedTeam){
        LogUtils.logRequest(log,this,"更新Team_ID：{}"+teamId);
        Team updatedTeam=teamRepository.findById(teamId)
                .orElseThrow(()-> new NotFoundException("找不到ID為"+teamId+"之JobLevel"));
        BeanUtil.copyNotNullProperties(inputtedTeam,updatedTeam);
        teamRepository.save(updatedTeam);
        LogUtils.logResponse(log,this,"更新Team_ID：{}"+teamId);
        return updatedTeam;
    }
}


