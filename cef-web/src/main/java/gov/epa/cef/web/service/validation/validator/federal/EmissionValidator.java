package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.exception.CalculationException;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import gov.epa.cef.web.util.CalculationUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.google.common.base.Strings;

@Component
public class EmissionValidator extends BaseValidator<Emission> {

    @Autowired
    private CefConfig cefConfig;
    
    private static final String ASH_EMISSION_FORMULA_CODE = "A";
    private static final String SULFUR_EMISSION_FORMULA_CODE = "SU";
    private static final String STATUS_TEMPORARILY_SHUTDOWN = "TS";
    private static final String STATUS_PERMANENTLY_SHUTDOWN = "PS";
    private static final String ENGINEERING_JUDGEMENT = "2";

    @Override
    public boolean validate(ValidatorContext validatorContext, Emission emission) {

        boolean valid = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);
        
        if (!emission.getReportingPeriod().getEmissionsProcess().getOperatingStatusCode().getCode().equals(STATUS_TEMPORARILY_SHUTDOWN)
                && !emission.getReportingPeriod().getEmissionsProcess().getOperatingStatusCode().getCode().equals(STATUS_PERMANENTLY_SHUTDOWN)) {

            if (emission.getPollutant() != null && emission.getPollutant().getLastInventoryYear() != null
                    && emission.getPollutant().getLastInventoryYear() < getReportYear(emission)) {

                valid = false;
                context.addFederalError(
                        ValidationField.EMISSION_POLLUTANT.value(),
                        "emission.pollutant.legacy", 
                        createValidationDetails(emission),
                        getPollutantName(emission));
            }

            if (emission.getEmissionsCalcMethodCode() == null) {

	            // prevented by db constraints
	            valid = false;
	            context.addFederalError(
	                ValidationField.EMISSION_CALC_METHOD.value(),
	                "emission.emissionsCalcMethodCode.required", 
	                createValidationDetails(emission));
	
            } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == true) {
	
	            if(Strings.emptyToNull(emission.getComments()) == null && ENGINEERING_JUDGEMENT.contentEquals(emission.getEmissionsCalcMethodCode().getCode())) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_COMMENTS.value(),
	                        "emission.comments.required.method", 
	                        createValidationDetails(emission));
	            }
	
	            if(emission.getEmissionsFactor() != null) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_EF.value(),
	                        "emission.emissionsFactor.banned.method",
	                        createValidationDetails(emission));
	            }
	
	            // This check is commented out, to be confirmed Post MVP
//	            if (emission.getReportingPeriod() != null 
//	                    && emission.getReportingPeriod().getCalculationParameterValue().compareTo(BigDecimal.ZERO) == 0
//	                    && emission.getTotalEmissions().compareTo(BigDecimal.ZERO) != 0) {
//	
//	                valid = false;
//	                context.addFederalError(
//	                        ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
//	                        "emission.totalEmissions.nonzero.method", 
//	                        createValidationDetails(emission));
//	            }
	
	        } else if (emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == false 
	                && !(Boolean.TRUE.equals(emission.getFormulaIndicator()) && Boolean.TRUE.equals(emission.getTotalManualEntry()))) {
	
	            if(emission.getEmissionsFactor() == null) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_EF.value(),
	                        "emission.emissionsFactor.required.method",
	                        createValidationDetails(emission));
	            }
	        }
	
	        if (emission.getEmissionsUomCode() != null && Boolean.TRUE.equals(emission.getEmissionsUomCode().getLegacy())) {
	
	            valid = false;
	            context.addFederalError(
	                    ValidationField.EMISSION_UOM.value(),
	                    "emission.emissionsUom.legacy", 
	                    createValidationDetails(emission),
	                    emission.getEmissionsUomCode().getDescription());
	        }
	
	        if (emission.getEmissionsFactor() != null || (Boolean.TRUE.equals(emission.getFormulaIndicator()) && Boolean.TRUE.equals(emission.getTotalManualEntry()))) {
	        	
	        	if (emission.getEmissionsFactor() != null && emission.getEmissionsFactor().compareTo(BigDecimal.ZERO) <= 0) {
	
	        		valid = false;
	        		context.addFederalError(
	        				ValidationField.EMISSION_EF.value(), 
	        				"emission.emissionsFactor.range",
	        				createValidationDetails(emission));
	        	}
	
	            if (emission.getEmissionsNumeratorUom() == null) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_NUM_UOM.value(),
	                        "emission.emissionsNumeratorUom.required.emissionsFactor", 
	                        createValidationDetails(emission));
	
	            } else if (Boolean.TRUE.equals(emission.getEmissionsNumeratorUom().getLegacy())) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_NUM_UOM.value(),
	                        "emission.emissionsNumeratorUom.legacy", 
	                        createValidationDetails(emission),
	                        emission.getEmissionsNumeratorUom().getDescription());
	            }
	
	            if (emission.getEmissionsDenominatorUom() == null) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_DENOM_UOM.value(),
	                        "emission.emissionsDenominatorUom.required.emissionsFactor",
	                        createValidationDetails(emission));
	
	            } else if (Boolean.TRUE.equals(emission.getEmissionsDenominatorUom().getLegacy())) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_DENOM_UOM.value(),
	                        "emission.emissionsDenominatorUom.legacy", 
	                        createValidationDetails(emission),
	                        emission.getEmissionsDenominatorUom().getDescription());
	            }
	
	        } else if (emission.getEmissionsFactor() == null) {
	
	            if (emission.getEmissionsNumeratorUom() != null) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_NUM_UOM.value(),
	                        "emission.emissionsNumeratorUom.banned.emissionsFactor", 
	                        createValidationDetails(emission));
	            }
	
	            if (emission.getEmissionsDenominatorUom() != null) {
	
	                valid = false;
	                context.addFederalError(
	                        ValidationField.EMISSION_DENOM_UOM.value(),
	                        "emission.emissionsDenominatorUom.banned.emissionsFactor", 
	                        createValidationDetails(emission));
	            }
	
	        }
	        
	        if (emission.getVariables() != null) {
		        List<EmissionFormulaVariable> efvList = emission.getVariables().stream()
		            .filter(var -> var.getVariableCode() != null)
		            .collect(Collectors.toList());
		        
		        // check for emission formula variable code % ash value to be between 0.01 and 30
	        	for (EmissionFormulaVariable formulaVar: efvList) {
	        		if (ASH_EMISSION_FORMULA_CODE.contentEquals(formulaVar.getVariableCode().getCode()) &&
	        				(formulaVar.getValue() == null || formulaVar.getValue().compareTo(BigDecimal.valueOf(0.01)) == -1 || formulaVar.getValue().compareTo(new BigDecimal(30)) == 1)) {
	     
	        			valid = false;
	        			context.addFederalError(
	        					ValidationField.EMISSION_FORMULA_VARIABLE.value(),
	        					"emission.formula.variable.ashRange", 
	        					createValidationDetails(emission));
	              
	        		}
	        	}
	        	
	        	// check for emission formula variable code % sulfur value to be between 0.01 and 10
	        	for (EmissionFormulaVariable formulaVar: efvList) {
	        		if (SULFUR_EMISSION_FORMULA_CODE.contentEquals(formulaVar.getVariableCode().getCode()) &&
	        				(formulaVar.getValue() == null || (formulaVar.getValue().compareTo(BigDecimal.valueOf(0.01)) == -1) || (formulaVar.getValue().compareTo(new BigDecimal(10)) == 1))) {
	        			
	        			valid = false;
	        			context.addFederalError(
	        					ValidationField.EMISSION_FORMULA_VARIABLE.value(),
	        					"emission.formula.variable.sulfurRange", 
	        					createValidationDetails(emission));
	        		}
	        	}
	        }
	
	        // Emission Calculation checks
	        if (emission.getEmissionsCalcMethodCode() != null 
	                && emission.getEmissionsCalcMethodCode().getTotalDirectEntry() == false
	                && emission.getTotalManualEntry() == false) {
	            
	            if (Boolean.TRUE.equals(emission.getFormulaIndicator())) {
	
	                try {
	                    CalculationUtils.calculateEmissionFormula(emission.getEmissionsFactorFormula(), emission.getVariables());
	                } catch (CalculationException e) {
	
	                    valid = false;
	                    context.addFederalError(
	                            ValidationField.EMISSION_FORMULA_VARIABLE.value(),
	                            "emission.formula.variable.missing", 
	                            createValidationDetails(emission),
	                            String.join(", ", e.getMissingVariables()));
	                }
	            }
	
	            boolean canCalculate = true;
	
	            if (emission.getReportingPeriod() != null 
	                    && emission.getReportingPeriod().getCalculationParameterUom() != null 
	                    && emission.getEmissionsFactor() != null
	                    && emission.getEmissionsNumeratorUom() != null
	                    && emission.getEmissionsDenominatorUom() != null
	                    && emission.getEmissionsUomCode() != null){
	
	                // Total emissions cannot be calculated with the given emissions factor because Throughput UoM {0} cannot be converted to Emission Factor Denominator UoM {1}. 
	                // Please adjust Units of Measure or choose the option "I prefer to calculate the total emissions myself."
	                if (!emission.getReportingPeriod().getCalculationParameterUom().getUnitType().equals(emission.getEmissionsDenominatorUom().getUnitType())) {
	
	                    canCalculate = false;
	                    valid = false;
	                    context.addFederalError(
	                            ValidationField.EMISSION_DENOM_UOM.value(),
	                            "emission.emissionsDenominatorUom.mismatch", 
	                            createValidationDetails(emission),
	                            emission.getReportingPeriod().getCalculationParameterUom().getDescription(),
	                            emission.getEmissionsDenominatorUom().getDescription());
	                }
	
	                //Total emissions cannot be calculated with the given emissions factor because Emission Factor Numerator UoM {0} cannot be converted to Total Emissions UoM {1}. 
	                // Please adjust Units of Measure or choose the option "I prefer to calculate the total emissions myself."
	                if (!emission.getEmissionsNumeratorUom().getUnitType().equals(emission.getEmissionsUomCode().getUnitType())) {
	
	                    canCalculate = false;
	                    valid = false;
	                    context.addFederalError(
	                            ValidationField.EMISSION_NUM_UOM.value(),
	                            "emission.emissionsNumeratorUom.mismatch", 
	                            createValidationDetails(emission),
	                            emission.getEmissionsNumeratorUom().getDescription(),
	                            emission.getEmissionsUomCode().getDescription());
	                }
	
	                if (canCalculate) {
	
	                    BigDecimal warningTolerance = cefConfig.getEmissionsTotalWarningTolerance();
	                    BigDecimal errorTolerance = cefConfig.getEmissionsTotalErrorTolerance();
	
	
	                    BigDecimal totalEmissions = calculateTotalEmissions(emission);
	
	                    // Total emissions listed for this pollutant are outside the acceptable range of +/-{0}% from {1} which is the calculated 
	                    // emissions based on the Emission Factor provided. Please recalculate the total emissions for this pollutant or choose the option 
	                    // "I prefer to calculate the total emissions myself."
	                    if (checkTolerance(totalEmissions, emission.getTotalEmissions(), errorTolerance)) {
	
	                        valid = false;
	                        context.addFederalError(
	                                ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
	                                "emission.totalEmissions.tolerance", 
	                                createValidationDetails(emission),
	                                errorTolerance.multiply(new BigDecimal("100")).toString(),
	                                totalEmissions.toString());
	                        
	                    } else if (checkTolerance(totalEmissions, emission.getTotalEmissions(), warningTolerance)) {
	
	                        valid = false;
	                        context.addFederalWarning(
	                                ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
	                                "emission.totalEmissions.tolerance", 
	                                createValidationDetails(emission),
	                                warningTolerance.multiply(new BigDecimal("100")).toString(),
	                                totalEmissions.toString());
	                    }
	                }
	            }
	        }
	        
	        if (emission.getPollutant() != null && ("605".contentEquals(emission.getPollutant().getPollutantCode()))) {
	
	          if (emission.getEmissionsUomCode() == null || !"CURIE".contentEquals(emission.getEmissionsUomCode().getCode())) {
		          
	          	valid = false;
		          context.addFederalError(
		              ValidationField.EMISSION_CURIE_UOM.value(),
		              "emission.emissionsCurieUom.required", 
		              createValidationDetails(emission));
	          }
	        }
	        
	        // total emissions must be >= 0
	        if (emission.getTotalEmissions() == null || emission.getTotalEmissions().compareTo(BigDecimal.ZERO) == -1) {
		     
		        	valid = false;
		        	context.addFederalError(
		        			ValidationField.EMISSION_TOTAL_EMISSIONS.value(),
		        			"emission.totalEmissions.range", 
		        			createValidationDetails(emission));
	        }
	
	        // percent overall control cannot be < 0 and cannot be >= 100 percent.
	        if (emission.getOverallControlPercent() != null
	        		&& (emission.getOverallControlPercent().compareTo(new BigDecimal(0)) == -1 
	        		|| emission.getOverallControlPercent().compareTo(new BigDecimal(100)) >= 0)) {
	        	
	        	valid = false;
	        	context.addFederalError(
	        			ValidationField.EMISSION_CONTROL_PERCENT.value(),
	        			"emission.controlPercent.range", 
	        			createValidationDetails(emission));
	        	
	        }
	        
	        // if totalManualEntry is selected and emission factor is used, calculation description is required
	        if (emission.getTotalManualEntry() != null && emission.getTotalManualEntry() == true
	        		&& emission.getEmissionsFactor() != null && Strings.emptyToNull(emission.getCalculationComment()) == null) {
	        	
	        	valid = false;
	        	context.addFederalError(
	        			ValidationField.EMISSION_CALC_DESC.value(),
	        			"emission.calculationDescription.required", 
	        			createValidationDetails(emission));
	        	
	        }
        }
        
        return valid;
    }

    private String getEmissionsUnitIdentifier(Emission emission) {
        if (emission.getReportingPeriod() != null && emission.getReportingPeriod().getEmissionsProcess() != null 
                && emission.getReportingPeriod().getEmissionsProcess().getEmissionsUnit() != null) {
            return emission.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getUnitIdentifier();
        }
        return null;
    }

    private String getEmissionsProcessIdentifier(Emission emission) {
        if (emission.getReportingPeriod() != null && emission.getReportingPeriod().getEmissionsProcess() != null) {
            return emission.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier();
        }
        return null;
    }

    private String getPollutantName(Emission emission) {
        if (emission.getPollutant() != null) {
            return emission.getPollutant().getPollutantName();
        }
        return null;
    }

    private int getReportYear(Emission emission) {
        return emission.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear().intValue();
    }

    private ValidationDetailDto createValidationDetails(Emission source) {

        String description = MessageFormat.format("Emission Unit: {0}, Emission Process: {1}, Pollutant: {2}", 
                getEmissionsUnitIdentifier(source),
                getEmissionsProcessIdentifier(source),
                getPollutantName(source));

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), getPollutantName(source), EntityType.EMISSION, description);
        if (source.getReportingPeriod() != null) {
            dto.getParents().add(new ValidationDetailDto(source.getReportingPeriod().getId(), null, EntityType.REPORTING_PERIOD));
        }
        return dto;
    }

    private BigDecimal calculateTotalEmissions(Emission emission) {

        boolean leapYear = emission.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear() % 4 == 0;

        BigDecimal totalEmissions = emission.getEmissionsFactor().multiply(emission.getReportingPeriod().getCalculationParameterValue());

        // convert units for denominator and throughput
        if (!emission.getReportingPeriod().getCalculationParameterUom().getCode().equals(emission.getEmissionsDenominatorUom().getCode())) {
            totalEmissions = CalculationUtils.convertUnits(emission.getReportingPeriod().getCalculationParameterUom().getCalculationVariable(), 
                    emission.getEmissionsDenominatorUom().getCalculationVariable(), leapYear).multiply(totalEmissions);
        }

        // convert units for numerator and total emissions
        if (!emission.getEmissionsUomCode().getCode().equals(emission.getEmissionsNumeratorUom().getCode())) {
            totalEmissions = CalculationUtils.convertUnits(emission.getEmissionsNumeratorUom().getCalculationVariable(), 
                    emission.getEmissionsUomCode().getCalculationVariable(), leapYear).multiply(totalEmissions);
        }

        if (emission.getOverallControlPercent() != null) {
            BigDecimal controlRate = new BigDecimal("100").subtract(emission.getOverallControlPercent()).divide(new BigDecimal("100"));
            totalEmissions = totalEmissions.multiply(controlRate);
        }

        return totalEmissions;
    }

    private boolean checkTolerance(BigDecimal calculatedValue, BigDecimal providedValue, BigDecimal tolerance) {

        return calculatedValue.subtract(providedValue).abs().compareTo(calculatedValue.multiply(tolerance)) > 0;
    }
}
