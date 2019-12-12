package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.repository.OperatingDetailRepository;
import gov.epa.cef.web.service.dto.OperatingDetailDto;
import gov.epa.cef.web.service.mapper.OperatingDetailMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OperatingDetailServiceImplTest extends BaseServiceTest {

    @Mock
    private OperatingDetailRepository operatingDetailRepo;

    @Mock
    private OperatingDetailMapper operatingDetailMapper;

    @Mock
    private EmissionsReportStatusServiceImpl emissionsReportStatusService;

    @InjectMocks
    private OperatingDetailServiceImpl operatingDetailServiceImpl;

    private OperatingDetail operatingDetail;
    private OperatingDetailDto operatingDetailDto;

    @Before
    public void init(){
        operatingDetail = new OperatingDetail();
        operatingDetail.setId(1L);
        List<OperatingDetail> operatingDetailList = new ArrayList<OperatingDetail>();
        operatingDetailList.add(operatingDetail);
        when(operatingDetailRepo.findById(1L)).thenReturn(Optional.of(operatingDetail));
        when(operatingDetailRepo.findById(2L)).thenReturn(Optional.empty());
        when(operatingDetailRepo.save(operatingDetail)).thenReturn(operatingDetail);
        when(emissionsReportStatusService.resetEmissionsReportForEntity(ArgumentMatchers.anyList(), ArgumentMatchers.any())).thenReturn(null);

        operatingDetailDto = new OperatingDetailDto();
        operatingDetailDto.setId(1L);
        when(operatingDetailMapper.toDto(operatingDetail)).thenReturn(operatingDetailDto);

    }

    @Test
    public void update_Should_OperatingDetailObject_When_OperatingDetailExists(){
        OperatingDetailDto operatingDetail = operatingDetailServiceImpl.update(operatingDetailDto);
        assertNotEquals(null, operatingDetail);
    }

}
