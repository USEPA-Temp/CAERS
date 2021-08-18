/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.SubmissionsReviewDasboardService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.SubmissionsReviewDashboardMapper;

/**
 * @author ahmahfou
 *
 */
@Service
public class SubmissionsReviewDasboardServiceImpl implements SubmissionsReviewDasboardService{

    @Autowired
    private SubmissionsReviewDashboardRepository repo;
    
    @Autowired
    private SubmissionsReviewDashboardMapper mapper;
    
    @Autowired
    private UserService userService;
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByReportStatusAndProgramSystemCode(ReportStatus reportStatus) {
    	UserDto currentUser=userService.getCurrentUser();
    	List<SubmissionsReviewDashboardView> reportsList=repo.findByReportStatusAndProgramSystemCode(reportStatus, currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }

    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsForCurrentUserProgramSystemForTheCurrentFiscalYear() {
        UserDto currentUser=userService.getCurrentUser();
        List<SubmissionsReviewDashboardView> reportsList=repo.findByProgramSystemCode(currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndReportStatusAndProgramSystemCode(Short reportYear, ReportStatus reportStatus){
        UserDto currentUser=userService.getCurrentUser();
    	List<SubmissionsReviewDashboardView> reportsList=repo.findByYearAndReportStatusAndProgramSystemCode(reportYear, reportStatus, currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndProgramSystemCode(Short reportYear){
        UserDto currentUser=userService.getCurrentUser();
        List<SubmissionsReviewDashboardView> reportsList=repo.findByYearAndProgramSystemCode(reportYear, currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }
    
}
