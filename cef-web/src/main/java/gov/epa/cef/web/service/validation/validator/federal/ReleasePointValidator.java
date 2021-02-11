package gov.epa.cef.web.service.validation.validator.federal;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.EisLatLongToleranceLookupRepository;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import gov.epa.cef.web.util.ConstantUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReleasePointValidator extends BaseValidator<ReleasePoint> {

    @Autowired
    private EisLatLongToleranceLookupRepository latLongToleranceRepo;

    private static final String FUGITIVE_RELEASE_POINT_CODE = "1";
    private static final BigDecimal DEFAULT_TOLERANCE = BigDecimal.valueOf(0.003).setScale(6, RoundingMode.DOWN);
    private static final String VELOCITY_UOM_FPS = "FPS";
    private static final String VELOCITY_UOM_FPM = "FPM";
    private static final String FLOW_RATE_UOM_ACFS = "ACFS";
    private static final String FLOW_RATE_UOM_ACFM = "ACFM";
    private static final String UOM_FT = "FT";
    private static final String DIAMETER_FORMULA  = ": Exit Gas Velocity = Flow Rate / (Pi * (Stack Diameter /2) ^ 2) (assuming a circular stack).";
    private static final String LENGTH_WIDTH_FORMULA = ": Exit Gas Velocity = Flow Rate / (Stack Length * Stack Width) (assuming a rectangular stack).";

    @Override
    public boolean validate(ValidatorContext validatorContext, ReleasePoint releasePoint) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        // Disable most validations for non-operating release points
        //Only run the following checks is the Release Point status is operating. Otherwise, these checks are moot b/c
        //the data will not be sent to EIS and the user shouldn't have to go back and update them. Only id, status,
        //and status year, and RP type are sent to EIS for units that are not operating.
        if (ConstantUtils.STATUS_OPERATING.contentEquals(releasePoint.getOperatingStatusCode().getCode())) {

            // STACK RELEASE POINT CHECKS
            if (!releasePoint.getTypeCode().getCode().equals(FUGITIVE_RELEASE_POINT_CODE)) {

    	        if (releasePoint.getExitGasTemperature() == null) {

    	            // Exit Gas Temperature is required.
    	            result = false;
    	            context.addFederalError(
    	                    ValidationField.RP_GAS_TEMP.value(),
    	                    "releasePoint.exitGasTemperature.required",
    	                    createValidationDetails(releasePoint));

    	        } else if (releasePoint.getExitGasTemperature() < -30 || releasePoint.getExitGasTemperature() > 4000) {

    	            // Exit Gas Temperature must be between -30 and 4000 degrees Fahrenheit.
    	            result = false;
    	            context.addFederalError(
    	                    ValidationField.RP_GAS_TEMP.value(),
    	                    "releasePoint.exitGasTemperature.range",
    	                    createValidationDetails(releasePoint));
    	        }

    	        // Exit Gas Velocity is required.
    	        if (releasePoint.getExitGasVelocity() == null) {

    	        	result = false;
    	        	context.addFederalError(
                    		ValidationField.RP_GAS_VELOCITY.value(),
                    		"releasePoint.exitGasVelocity.required",
                    		createValidationDetails(releasePoint));
    	        }

    	     // Exit Gas Flow Rate is required.
    	        if (releasePoint.getExitGasFlowRate() == null) {

    	        	result = false;
    	        	context.addFederalError(
                    		ValidationField.RP_GAS_FLOW.value(),
                    		"releasePoint.exitGasFlowRate.required",
                    		createValidationDetails(releasePoint));
    	        }

    	        // Stack Height is required
    	        if (releasePoint.getStackHeight() == null) {

    	        	result = false;
    	        	context.addFederalError(
    	        			ValidationField.RP_STACK.value(),
    	        			"releasePoint.stack.required",
    	        			createValidationDetails(releasePoint),
    	        			"Stack Height");

                // Stack Height range
    	        } else if (releasePoint.getStackHeight() < 1 || releasePoint.getStackHeight() > 1300) {

    	            result = false;
    	            context.addFederalError(
    	                    ValidationField.RP_STACK.value(),
    	                    "releasePoint.stack.heightRange",
    	                    createValidationDetails(releasePoint));
    	        }

    	        // Stack Diameter or stack length/width is required
    	        if (releasePoint.getStackDiameter() == null && ( releasePoint.getStackWidth() == null || releasePoint.getStackLength() == null)) {

    	            result = false;
    	            context.addFederalError(
    	                ValidationField.RP_STACK.value(),
                        "releasePoint.stack.diameterOrLengthWidth",
                        createValidationDetails(releasePoint));

    	            // Only stack diameter or width/length allowed
    	        } else if (releasePoint.getStackDiameter() != null && (releasePoint.getStackLength() != null || releasePoint.getStackWidth() != null)) {
    	            result = false;
    	            context.addFederalError(
    	                ValidationField.RP_STACK.value(),
                        "releasePoint.stack.noDiameterAndLengthWidth",
                        createValidationDetails(releasePoint)
                    );

                }


    	        // Stack Diameter range
    	        if (releasePoint.getStackDiameter() != null && (releasePoint.getStackDiameter() < 0.001 || releasePoint.getStackDiameter() > 300)) {

    	            result = false;
    	            context.addFederalError(
    	            		ValidationField.RP_STACK.value(),
    	            		"releasePoint.stack.diameterRange",
    	            		createValidationDetails(releasePoint));
    	        }

    	        // Stack dimensions must be in FT
    	        if (!validateUomFT(validatorContext, releasePoint, releasePoint.getStackHeight(), releasePoint.getStackHeightUomCode(), "Stack Height")) {
    	        	result = false;
            	}

    	        if (!validateUomFT(validatorContext, releasePoint, releasePoint.getStackDiameter(), releasePoint.getStackDiameterUomCode(), "Stack Diameter")) {
            		result = false;
            	}

    	        // Calculation check, Stack Diameter must be less than Stack Height
    	        if (releasePoint.getStackDiameter() != null
    	        	&& releasePoint.getStackHeight() != null
    	        	&& (releasePoint.getStackDiameter() >= releasePoint.getStackHeight())) {

    	        	result = false;
    	        	context.addFederalWarning(
    	        			ValidationField.RP_STACK_WARNING.value(),
    	        			"releasePoint.stackWarning.diameterCheck.height",
    	        			createValidationDetails(releasePoint));
    	        }

    	        // Check exit gas velocity if exit gas flow rate and stack diameter are submitted.
    	        if ((releasePoint.getExitGasFlowRate() != null && releasePoint.getExitGasFlowRate() > 0)
    	        	&& ((releasePoint.getStackDiameter() != null && releasePoint.getStackDiameter() > 0)
                    || (releasePoint.getStackWidth() != null && releasePoint.getStackWidth() > 0 && releasePoint.getStackLength() != null && releasePoint.getStackLength() > 0)
                    )) {

    	        	BigDecimal minVelocity = BigDecimal.valueOf(0.001);
    	        	BigDecimal maxVelocity = BigDecimal.valueOf(1500.0);
    	        	BigDecimal calcVelocity = new BigDecimal(0);
    	        	double inputFlowRate = releasePoint.getExitGasFlowRate();
    	        	Double inputDiameter = null;
    	        	boolean isDiameter = releasePoint.getStackDiameter() != null && releasePoint.getStackDiameter() > 0;
    	        	String formula = isDiameter ? DIAMETER_FORMULA : LENGTH_WIDTH_FORMULA;

    	        	if (isDiameter)
    	        	    inputDiameter = releasePoint.getStackDiameter();

    	        	String uom= VELOCITY_UOM_FPS;

    	        	double calcArea = isDiameter ? (Math.PI)*(Math.pow((inputDiameter/2), 2)) : ( releasePoint.getStackWidth() * releasePoint.getStackLength());
    	        	calcVelocity = BigDecimal.valueOf(inputFlowRate/calcArea).setScale(3, RoundingMode.HALF_UP);

    	        	if (releasePoint.getExitGasFlowUomCode() != null && !FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode())) {
    	        		minVelocity = BigDecimal.valueOf(0.060);
    	        		maxVelocity = new BigDecimal(90000);
    	        		uom = VELOCITY_UOM_FPM;
    	        	}

    	        	if (calcVelocity.compareTo(maxVelocity.setScale(3, RoundingMode.HALF_UP)) == 1 || calcVelocity.compareTo(minVelocity.setScale(3, RoundingMode.HALF_UP)) == -1) {

                        result = false;
    	        		context.addFederalError(
    	        				ValidationField.RP_GAS_VELOCITY.value(),
    	        				"releasePoint.exitGasVelocity.range",
    	        				createValidationDetails(releasePoint),
    	        				calcVelocity.toString(),
    	        				uom,
    	        				formula,
    	        				minVelocity,
    	        				maxVelocity,
    	        				uom);
    	        	}
    	        }

    	        // Check exit gas flow rate if exit gas flow rate, exit gas velocity, and stack diameter are submitted.
    	        if ((releasePoint.getExitGasVelocity() != null && releasePoint.getExitGasVelocity() > 0)
    	        	&& (releasePoint.getExitGasFlowRate() != null && releasePoint.getExitGasFlowRate() > 0)
    	        	&& (releasePoint.getStackDiameter() != null && releasePoint.getStackDiameter() > 0)) {

    	        	BigDecimal inputFlowRate = new BigDecimal(releasePoint.getExitGasFlowRate());
    	        	double inputVelocity = releasePoint.getExitGasVelocity();
    	        	double inputDiameter = releasePoint.getStackDiameter();

    	        	double calcArea = (Math.PI)*(Math.pow((inputDiameter/2), 2));
    	        	double calcFlowRate = (inputVelocity*calcArea);
    	        	BigDecimal lowerLimitFlowRate = new BigDecimal(0);
    	        	BigDecimal upperLimitFlowRate = new BigDecimal(0);
    	        	String uom= FLOW_RATE_UOM_ACFS;

    	        	if (releasePoint.getExitGasVelocityUomCode() != null && !VELOCITY_UOM_FPS.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode())) {
    	        		uom= FLOW_RATE_UOM_ACFM;
    	        	}

    	        	// set actual flow rate UoM to compare to computed flow rate
    	        	if (releasePoint.getExitGasFlowUomCode() != null) {
    	        		if (!FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) && FLOW_RATE_UOM_ACFS.contentEquals(uom)) {
    		        		inputFlowRate = BigDecimal.valueOf((releasePoint.getExitGasFlowRate()/60));
    		        	} else if (!FLOW_RATE_UOM_ACFM.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) && FLOW_RATE_UOM_ACFM.contentEquals(uom)) {
    		        		inputFlowRate = BigDecimal.valueOf((releasePoint.getExitGasFlowRate()*60));
    		        	}
    	        	}

    	        	lowerLimitFlowRate = BigDecimal.valueOf(0.95*calcFlowRate).setScale(8, RoundingMode.HALF_UP);
    	        	upperLimitFlowRate = BigDecimal.valueOf(1.05*calcFlowRate).setScale(8, RoundingMode.HALF_UP);

    	        	if (!((inputFlowRate.setScale(8, RoundingMode.HALF_UP)).equals(BigDecimal.valueOf(0.00000001).setScale(8, RoundingMode.HALF_UP)))
    		        	&& (((inputFlowRate.setScale(8, RoundingMode.HALF_UP)).compareTo(upperLimitFlowRate) == 1)
    		        	|| ((inputFlowRate.setScale(8, RoundingMode.HALF_UP)).compareTo(lowerLimitFlowRate) == -1))) {

    	        		result = false;
    	        		context.addFederalError(
    	        				ValidationField.RP_GAS_FLOW.value(),
    	        				"releasePoint.exitGasFlowRate.range",
    	        				createValidationDetails(releasePoint),
    	        				BigDecimal.valueOf(calcFlowRate).setScale(8, RoundingMode.HALF_UP).toString(),
    	        				uom);
    	        	}
            	}
            }

            // FUGITIVE RELEASE POINT CHECKS
            if (releasePoint.getTypeCode().getCode().equals(FUGITIVE_RELEASE_POINT_CODE)) {

            	// Fugitive Height Range
            	if (releasePoint.getFugitiveHeight() != null
            		&& (releasePoint.getFugitiveHeight() < 0 || releasePoint.getFugitiveHeight() > 500)) {

    	        	result = false;
    	        	context.addFederalError(
    	        			ValidationField.RP_FUGITIVE.value(),
    	        			"releasePoint.fugitive.heightRange",
    	        			createValidationDetails(releasePoint));
            	}

            	// Fugitive Length Range
            	if (releasePoint.getFugitiveLength() != null
              		&& (releasePoint.getFugitiveLength() < 1 || releasePoint.getFugitiveLength() > 10000)) {

      	        	result = false;
      	        	context.addFederalError(
      	        			ValidationField.RP_FUGITIVE.value(),
      	        			"releasePoint.fugitive.lengthRange",
      	        			createValidationDetails(releasePoint));
              }

            	// Fugitive Width Range
            	if (releasePoint.getFugitiveWidth() != null
              		&& (releasePoint.getFugitiveWidth() < 1 || releasePoint.getFugitiveWidth() > 10000)) {

      	        	result = false;
      	        	context.addFederalError(
      	        			ValidationField.RP_FUGITIVE.value(),
      	        			"releasePoint.fugitive.widthRange",
      	        			createValidationDetails(releasePoint));
              }

            	// Fugitive Angle Range
            	if (releasePoint.getFugitiveAngle() != null
              		&& (releasePoint.getFugitiveAngle() < 0 || releasePoint.getFugitiveAngle() > 89)) {

      	        	result = false;
      	        	context.addFederalError(
      	        			ValidationField.RP_FUGITIVE.value(),
      	        			"releasePoint.fugitive.angleRange",
      	        			createValidationDetails(releasePoint));
              }

            	// Fugitive Dimensions must be in FT
            	if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFugitiveLength(), releasePoint.getFugitiveLengthUomCode(), "Fugitive Length")) {
            		result = false;
            	}

            	if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFugitiveWidth(), releasePoint.getFugitiveWidthUomCode(), "Fugitive Width")) {
            		result = false;
            	}

            	if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFugitiveHeight(), releasePoint.getFugitiveHeightUomCode(), "Fugitive Height")) {
            		result = false;
            	}
            }

            if ((releasePoint.getExitGasFlowRate() != null && releasePoint.getExitGasFlowUomCode() == null) ||
              	(releasePoint.getExitGasFlowRate() == null && releasePoint.getExitGasFlowUomCode() != null)) {

              	// Exit Gas Flow Rate and Exit Gas Flow Rate UoM must be reported together.
              	result = false;
              	context.addFederalError(
                        ValidationField.RP_GAS_FLOW.value(),
                        "releasePoint.exitGasFlowRate.uom.required",
                        createValidationDetails(releasePoint));

              } else if (releasePoint.getExitGasFlowUomCode() != null && (
              	!FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
              	!FLOW_RATE_UOM_ACFM.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()))) {

              	// Exit Gas Flow Rate UoM must be in ACFS or ACFM.
              	result = false;
              	context.addFederalError(
                        ValidationField.RP_GAS_FLOW.value(),
                        "releasePoint.exitGasFlowRate.requiredUom",
                        createValidationDetails(releasePoint));
              }

              // Exit Gas Flow Rate Ranges
              if (releasePoint.getExitGasFlowUomCode() != null && releasePoint.getExitGasFlowRate() != null) {
              	if (FUGITIVE_RELEASE_POINT_CODE.contentEquals(releasePoint.getTypeCode().getCode())) {
              		if (FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
              				(releasePoint.getExitGasFlowRate() < 0 || releasePoint.getExitGasFlowRate() > 200000)) {

              				result = false;
              				context.addFederalError(
      			                  ValidationField.RP_GAS_FLOW.value(),
      			                  "releasePoint.exitGasFlowRate.fugitiveACFS.range",
      			                  createValidationDetails(releasePoint));

      	        	} else if (!FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
      	        			(releasePoint.getExitGasFlowRate() < 0 || releasePoint.getExitGasFlowRate() > 12000000)) {
      		        		result = false;
      			        	context.addFederalError(
      			                  ValidationField.RP_GAS_FLOW.value(),
      			                  "releasePoint.exitGasFlowRate.fugitiveACFM.range",
      			                  createValidationDetails(releasePoint));
      	        	}
              	} else {
              		if (FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
              				(releasePoint.getExitGasFlowRate() < 0.00000001 || releasePoint.getExitGasFlowRate() > 200000)) {

              				result = false;
              				context.addFederalError(
      			                  ValidationField.RP_GAS_FLOW.value(),
      			                  "releasePoint.exitGasFlowRate.stackACFS.range",
      			                  createValidationDetails(releasePoint));

              		} else if (!FLOW_RATE_UOM_ACFS.contentEquals(releasePoint.getExitGasFlowUomCode().getCode()) &&
      	        			(releasePoint.getExitGasFlowRate() < 0.00000001 || releasePoint.getExitGasFlowRate() > 12000000)) {

              				result = false;
      			        	context.addFederalError(
      			                  ValidationField.RP_GAS_FLOW.value(),
      			                  "releasePoint.exitGasFlowRate.stackACFM.range",
      			                  createValidationDetails(releasePoint));
      	        	}
              	}
              }

              // Exit Gas Velocity Ranges
              if (releasePoint.getExitGasVelocityUomCode() != null && releasePoint.getExitGasVelocity() != null) {
              	if (FUGITIVE_RELEASE_POINT_CODE.contentEquals(releasePoint.getTypeCode().getCode())) {
              		if (VELOCITY_UOM_FPS.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
              				(releasePoint.getExitGasVelocity() < 0 || releasePoint.getExitGasVelocity() > 400)) {

              				result = false;
              				context.addFederalError(
      			                  ValidationField.RP_GAS_VELOCITY.value(),
      			                  "releasePoint.exitGasVelocity.fugitiveFPS.range",
      			                  createValidationDetails(releasePoint));

      	        	} else if (!VELOCITY_UOM_FPS.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
      	        			(releasePoint.getExitGasVelocity() < 0 || releasePoint.getExitGasVelocity() > 24000)) {

      		        		result = false;
      			        	context.addFederalError(
      			                  ValidationField.RP_GAS_VELOCITY.value(),
      			                  "releasePoint.exitGasVelocity.fugitiveFPM.range",
      			                  createValidationDetails(releasePoint));
      	        	}
              	} else {
              		if (VELOCITY_UOM_FPS.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
              				(releasePoint.getExitGasVelocity() < 0.001 || releasePoint.getExitGasVelocity() > 1500)) {

              				result = false;
              				context.addFederalError(
      			                  ValidationField.RP_GAS_VELOCITY.value(),
      			                  "releasePoint.exitGasVelocity.stackFPS.range",
      			                  createValidationDetails(releasePoint));

              		} else if (!VELOCITY_UOM_FPS.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
      	        			(releasePoint.getExitGasVelocity() < 0.060 || releasePoint.getExitGasVelocity() > 90000)) {

              				result = false;
      			        	context.addFederalError(
      			                  ValidationField.RP_GAS_VELOCITY.value(),
      			                  "releasePoint.exitGasVelocity.stackFPM.range",
      			                  createValidationDetails(releasePoint));
      	        	}
              	}
              }

              if ((releasePoint.getExitGasVelocity() != null && releasePoint.getExitGasVelocityUomCode() == null) ||
              	(releasePoint.getExitGasVelocity() == null && releasePoint.getExitGasVelocityUomCode() != null)) {

              	// Exit Gas Velocity and Exit Gas Velocity UoM must be reported together.
              	result = false;
              	context.addFederalError(
                        ValidationField.RP_GAS_VELOCITY.value(),
                        "releasePoint.exitGasVelocity.uom.required",
                        createValidationDetails(releasePoint));

              } else if (releasePoint.getExitGasVelocityUomCode() != null && (
              	!VELOCITY_UOM_FPS.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()) &&
              	!VELOCITY_UOM_FPM.contentEquals(releasePoint.getExitGasVelocityUomCode().getCode()))) {

              	// Exit Gas Velocity UoM must be in FPM or FPS.
              	result = false;
              	context.addFederalError(
                        ValidationField.RP_GAS_VELOCITY.value(),
                        "releasePoint.exitGasVelocity.requiredUom",
                        createValidationDetails(releasePoint));
              }

            // Fence Line Distance Range
            if (releasePoint.getFenceLineDistance() != null
            		&& (releasePoint.getFenceLineDistance() < 0 || releasePoint.getFenceLineDistance() > 99999)) {

            	result = false;
            	context.addFederalError(
            			ValidationField.RP_FENCELINE.value(),
            			"releasePoint.fenceLine.range",
            			createValidationDetails(releasePoint));
          	}

            // Fence Line Distance must be in FT
            if (!validateUomFT_long(validatorContext, releasePoint, releasePoint.getFenceLineDistance(), releasePoint.getFenceLineUomCode(), "Fence Line Distance")) {
          		result = false;
          	}

            // Latitude/Longitude warning, must have both or value will not be submitted
            if ((releasePoint.getLatitude() == null && releasePoint.getLongitude() != null)
        		|| (releasePoint.getLatitude() != null && releasePoint.getLongitude() == null)){

            	result = false;
            	context.addFederalWarning(
            			ValidationField.RP_COORDINATE.value(),
            			"releasePoint.coordinate.warning",
            			createValidationDetails(releasePoint));
            }

            // Latitude/Longitude Tolerance Check
            if (releasePoint.getLatitude() != null && releasePoint.getLongitude() != null) {
    	        BigDecimal facilitySiteLat = releasePoint.getFacilitySite().getLatitude();
    	        BigDecimal facilitySiteLong = releasePoint.getFacilitySite().getLongitude();

    	        if (!validateCoordinateTolerance(validatorContext, releasePoint, releasePoint.getLatitude(), facilitySiteLat, "latitude", "latitude")) {
    	        	result = false;
    	        }

    	        if (!validateCoordinateTolerance(validatorContext, releasePoint, releasePoint.getLongitude(), facilitySiteLong, "longitude", "longitude")) {
    	        	result = false;
    	        }
            }

            if (releasePoint.getTypeCode() != null && releasePoint.getTypeCode().getLastInventoryYear() != null
                && releasePoint.getTypeCode().getLastInventoryYear() < releasePoint.getFacilitySite().getEmissionsReport().getYear()) {

                result = false;
                context.addFederalError(
                        ValidationField.RP_TYPE_CODE.value(),
                        "releasePoint.releasePointTypeCode.legacy",
                        createValidationDetails(releasePoint),
                        releasePoint.getTypeCode().getDescription());

            }

        }

        // CHECKS FOR ALL RELEASE POINT TYPES

        // If release point operation is apportioned to a process, operation status must be operating
        boolean hasApportionment = !releasePoint.getReleasePointAppts().isEmpty();
        boolean isRelPointOperating = ConstantUtils.STATUS_OPERATING.contentEquals(releasePoint.getOperatingStatusCode().getCode());

        if (hasApportionment && !isRelPointOperating) {
            List<ReleasePointAppt> appts = releasePoint.getReleasePointAppts();
            for (ReleasePointAppt appt : appts) {
                boolean isProcessOperating = ConstantUtils.STATUS_OPERATING.contentEquals(appt.getEmissionsProcess().getOperatingStatusCode().getCode());

                if (isProcessOperating) {
                    result = false;
                    context.addFederalError(
                        ValidationField.RP_STATUS_CODE.value(), "releasePoint.statusTypeCode.apportioned",
                        createValidationDetails(releasePoint),
                        releasePoint.getOperatingStatusCode().getDescription()
                    );
                    break;
                }
            }
        }

        // If release point operation status is not operating, status year is required
        if (!ConstantUtils.STATUS_OPERATING.contentEquals(releasePoint.getOperatingStatusCode().getCode()) && releasePoint.getStatusYear() == null) {

            result = false;
            context.addFederalError(
                    ValidationField.RP_STATUS_CODE.value(), "releasePoint.statusTypeCode.required",
                    createValidationDetails(releasePoint));
        }

        // Status year must be between 1900 and 2050
        if (releasePoint.getStatusYear() != null && (releasePoint.getStatusYear() < 1900 || releasePoint.getStatusYear() > 2050)) {

            result = false;
            context.addFederalError(
                    ValidationField.RP_STATUS_YEAR.value(), "releasePoint.statusYear.range",
                    createValidationDetails(releasePoint));
        }

        if (releasePoint != null && releasePoint.getFacilitySite() != null && releasePoint.getFacilitySite().getReleasePoints() != null) {
            // Release Point Identifier must be unique within the facility.
          Map<Object, List<ReleasePoint>> rpMap = releasePoint.getFacilitySite().getReleasePoints().stream()
                  .filter(rp -> (rp.getReleasePointIdentifier() != null))
                  .collect(Collectors.groupingBy(frp -> frp.getReleasePointIdentifier()));

            for (List<ReleasePoint> rpList : rpMap.values()) {

            if (rpList.size() > 1 && rpList.get(0).getReleasePointIdentifier().contentEquals(releasePoint.getReleasePointIdentifier())) {

                result = false;
                context.addFederalError(
                        ValidationField.RP_IDENTIFIER.value(),
                        "releasePoint.releasePointIdentifier.duplicate",
                        createValidationDetails(releasePoint));
            }
          }
        }

        return result;
    }


    public boolean validateUomFT(ValidatorContext validatorContext, ReleasePoint releasePoint, Double measure, UnitMeasureCode uom, String uomField) {

      CefValidatorContext context = getCefValidatorContext(validatorContext);
      boolean result = true;

      if ((uom != null && !UOM_FT.contentEquals(uom.getCode())) || (uom == null && measure != null)) {

      	result = false;
      	context.addFederalError(
      			ValidationField.RP_UOM_FT.value(),
      			"releasePoint.uom.ft",
      			createValidationDetails(releasePoint),
      			uomField);
  		  }

      return result;
    }

    public boolean validateUomFT_long(ValidatorContext validatorContext, ReleasePoint releasePoint, Long measure, UnitMeasureCode uom, String uomField) {
    	if (measure != null) {
    		return validateUomFT(validatorContext, releasePoint, Double.valueOf(measure), uom, uomField);
    	} else {
    		return validateUomFT(validatorContext, releasePoint, null, uom, uomField);
    	}
    }

    public boolean validateCoordinateTolerance(ValidatorContext validatorContext, ReleasePoint releasePoint, Double rpCoordinate,  BigDecimal facilityCoordinate, String rpLatLongField, String facilityLatLongField) {

      CefValidatorContext context = getCefValidatorContext(validatorContext);
      boolean result = true;
      BigDecimal maxRange;
      BigDecimal minRange;
      BigDecimal releasePointCoordinate = null;
      BigDecimal facilityTolerance = DEFAULT_TOLERANCE;

      String facilityEisId = releasePoint.getFacilitySite().getEisProgramId();

      if (!rpCoordinate.equals(null)) {
      	releasePointCoordinate = (new BigDecimal(rpCoordinate)).setScale(6, RoundingMode.HALF_UP);
    	}

      if (latLongToleranceRepo.findById(facilityEisId).orElse(null) == null) {
      	maxRange = facilityCoordinate.add(facilityTolerance).setScale(6, RoundingMode.HALF_UP);
      	minRange = facilityCoordinate.subtract(facilityTolerance).setScale(6, RoundingMode.HALF_UP);
      } else {
      	facilityTolerance = latLongToleranceRepo.findById(facilityEisId).orElse(null).getCoordinateTolerance().setScale(6, RoundingMode.HALF_UP);
      	maxRange = facilityCoordinate.add(facilityTolerance).setScale(6, RoundingMode.HALF_UP);
      	minRange = facilityCoordinate.subtract(facilityTolerance).setScale(6, RoundingMode.HALF_UP);
      }

      if (releasePointCoordinate == null || (releasePointCoordinate.compareTo(maxRange) == 1) || (releasePointCoordinate.compareTo(minRange) == -1)) {

      	result = false;
      	context.addFederalError(
      			ValidationField.RP_COORDINATE.value(),
      			"releasePoint.coordinate.tolerance.facilityRange",
      			createValidationDetails(releasePoint),
      			rpLatLongField,
      			Double.valueOf(facilityTolerance.toString()).toString(),
      			facilityLatLongField,
      			facilityCoordinate.setScale(6, RoundingMode.HALF_UP).toString());
    	}

    	return result;
    }

    private ValidationDetailDto createValidationDetails(ReleasePoint source) {

        String description = MessageFormat.format("Release Point: {0}", source.getReleasePointIdentifier());

        ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getReleasePointIdentifier(), EntityType.RELEASE_POINT, description);
        return dto;
    }
}
