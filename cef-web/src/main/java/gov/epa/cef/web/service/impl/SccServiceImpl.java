package gov.epa.cef.web.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.epa.cef.web.client.api.SccApiClient;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.repository.PointSourceSccCodeRepository;
import gov.epa.cef.web.service.SccService;
import gov.epa.cef.web.service.dto.SccAttributeDto;
import gov.epa.cef.web.service.dto.SccDetailDto;
import gov.epa.client.sccwebservices.model.SccDetail;

@Service
public class SccServiceImpl implements SccService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PointSourceSccCodeRepository pointSourceSccCodeRepo;

    @Autowired
    private SccApiClient sccClient;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Retrieve Point SCCs from the webservice since a certain date
     * @param lastUpdated
     * @return
     */
    private List<SccDetailDto> retrievePointSccDetailsSince(String lastUpdated) {

        List<String> facetName = Collections.singletonList("Data Category");
        List<String> facetValue = Collections.singletonList("Point");
        List<String> facetQualifier = Collections.singletonList("exact");
        List<String> facetMatchType = Collections.singletonList("whole_phrase");

        List<SccDetailDto> result = this.sccClient.getResourceSearchResults(facetName, facetValue, facetQualifier, facetMatchType, null, null, null, lastUpdated, null, null, null)
                .stream()
                .map(scc -> {
                    SccDetailDto dto = mapSccDetail(scc);

                    return dto;
                }).collect(Collectors.toList());

        return result;
    }

    /**
     * Retrieve Point SCCs from the webservice since a certain date and update the database with them
     * @param lastUpdated
     * @return
     */
    @Override
    public Iterable<PointSourceSccCode> updatePointSourceSccCodes(String lastUpdated) {

        List<SccDetailDto> updatedCodes = this.retrievePointSccDetailsSince(lastUpdated);
        List<PointSourceSccCode> codeEntities = updatedCodes.stream().map(dto -> {
            PointSourceSccCode entity = new PointSourceSccCode();
            entity.setCode(dto.getCode());
            if (dto.getAttributes().containsKey("last inventory year")) {
                entity.setLastInventoryYear(Short.valueOf(dto.getAttributes().get("last inventory year").getText()));
            }
            return entity;
        }).collect(Collectors.toList());

        return this.pointSourceSccCodeRepo.saveAll(codeEntities);
    }

    /**
     * Map SCCs from the webservice into a class with the correct datatypes
     * @param detail
     * @return
     */
    private SccDetailDto mapSccDetail(SccDetail detail) {
        SccDetailDto result = new SccDetailDto();

        result.setUid(detail.getUid());
        result.setCode(detail.getCode());
        result.setLastUpdated(detail.getLastUpdated());
        result.setAttributes(objectMapper.convertValue(detail.getAttributes(),  new TypeReference<Map<String, SccAttributeDto>>() {}));

        return result;
    }
}
