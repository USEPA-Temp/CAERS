package gov.epa.cef.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionFactorVariableCode;
import gov.epa.cef.web.repository.EmissionFactorRepository;
import gov.epa.cef.web.repository.EmissionFactorVariableCodeRepository;
import gov.epa.cef.web.service.EmissionFactorService;
import gov.epa.cef.web.service.dto.EmissionFactorDto;
import gov.epa.cef.web.service.dto.EfVariableCodeDto;
import gov.epa.cef.web.service.mapper.EmissionFactorMapper;
import gov.epa.cef.web.service.mapper.LookupEntityMapper;

@Service
public class EmissionFactorServiceImpl implements EmissionFactorService {

    @Autowired
    private EmissionFactorRepository efRepo;

    @Autowired
    private EmissionFactorVariableCodeRepository efVariableRepo;

    @Autowired
    private EmissionFactorMapper mapper;

    @Autowired
    private LookupEntityMapper lookupMapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.EmissionFactorService#retrieveByExample(gov.epa.cef.web.service.dto.EmissionFactorDto)
     */
    @Override
    public List<EmissionFactorDto> retrieveByExample(EmissionFactorDto dto) {
        List<EmissionFactorDto> result = mapper.toDtoList(efRepo.findAll(Example.of(mapper.fromDto(dto))));

        result.forEach(ef -> {
            if (Boolean.TRUE.equals(ef.getFormulaIndicator())) {
                ef.setVariables(parseFormulaVariables(ef.getEmissionFactorFormula()));
            }
        });

        return result;
    }

    private List<EfVariableCodeDto> parseFormulaVariables(String formula) {
        // Sorting by code length in descending order will ensure that variables are identified correctly. 
        // Should also be able to add new variables without code changes.
        List<EmissionFactorVariableCode> variables = efVariableRepo.findAll(JpaSort.unsafe(Sort.Direction.DESC, "LENGTH(code)"));
        List<EfVariableCodeDto> result = new ArrayList<EfVariableCodeDto>();

        for (EmissionFactorVariableCode variable : variables) {
            if (formula.contains(variable.getCode())) {
                formula = formula.replaceAll(variable.getCode(), "");
                result.add(lookupMapper.emissionFactorVariableCodeToDto(variable));
            }
        }

        return result;
    }
}
