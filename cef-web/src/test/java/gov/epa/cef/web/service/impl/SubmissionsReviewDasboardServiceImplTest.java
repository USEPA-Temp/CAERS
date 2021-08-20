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

import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.SubmissionsReviewDashboardMapper;
import gov.epa.cef.web.util.DateUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Category(TestCategories.FastTest.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtils.class})
public class SubmissionsReviewDasboardServiceImplTest {

    @Mock
    private SubmissionsReviewDashboardRepository repo;

    @Mock
    private SubmissionsReviewDashboardMapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    SubmissionsReviewDasboardServiceImpl submissionsReviewDasboardServiceImpl;


    @Test
    public void retrieveFacilitiesReports_Should_ReturnListOfSubmissionsUnderReview_When_NoArugmentsPassed() {

        UserDto user=new UserDto();
        user.setProgramSystemCode("GADNR");
        when(userService.getCurrentUser()).thenReturn(user);

        Date date=Calendar.getInstance().getTime();
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance().getTime()).thenReturn(date);
        when(DateUtils.getFiscalYearForDate(date)).thenReturn(2019);

        List<SubmissionsReviewDashboardView> submissionsReviewDashboardView=new ArrayList<>();
        when(repo.findByProgramSystemCode("GADNR")).thenReturn(submissionsReviewDashboardView);

        List<SubmissionsReviewDashboardDto> submissionsReviewDashboardDto=new ArrayList<>();
        when(mapper.toDtoList(submissionsReviewDashboardView)).thenReturn(submissionsReviewDashboardDto);

        List<SubmissionsReviewDashboardDto> result= submissionsReviewDasboardServiceImpl.retrieveFacilitiesReportsForCurrentUserProgramSystemForTheCurrentFiscalYear();

        assertEquals(submissionsReviewDashboardDto, result);
    }

}
