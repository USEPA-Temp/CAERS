package gov.epa.cef.web.service.validation.validator.federal;

import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.BaseValidator;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

@Component
public class OperatingDetailValidator extends BaseValidator<OperatingDetail> {

    @Override
    public boolean validate(ValidatorContext validatorContext, OperatingDetail detail) {

        boolean result = true;

        CefValidatorContext context = getCefValidatorContext(validatorContext);

        if (detail.getAvgHoursPerDay() == null) {

            // Average Hours/Day is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgHoursPerDay",
                    "operatingDetail.avgHoursPerDay.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getAvgHoursPerDay() > 24 || detail.getAvgHoursPerDay() < .1) {

            // Average Hours Per Day must be between 0.1 and 24
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgHoursPerDay",
                    "operatingDetail.avgHoursPerDay.range", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));
        }

        if (detail.getAvgDaysPerWeek() == null) {

            // Average Days/Week is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgDaysPerWeek",
                    "operatingDetail.avgDaysPerWeek.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getAvgDaysPerWeek() > 7 || detail.getAvgDaysPerWeek() < .1) {

            // Average Days Per Week must be between 0.1 and 7
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgDaysPerWeek",
                    "operatingDetail.avgDaysPerWeek.range", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));
        }

        if (detail.getActualHoursPerPeriod() == null) {

            //  Actual Hours/Year is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.actualHoursPerPeriod",
                    "operatingDetail.actualHoursPerPeriod.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getActualHoursPerPeriod() > 8784 || detail.getActualHoursPerPeriod() < 1) {

            // Actual Hours Per Year must be between 1 and 8784
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.actualHoursPerPeriod",
                    "operatingDetail.actualHoursPerPeriod.range", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));
        }

        
        int seasons = 0; // number of seasons with percents entered
        double total = 0; // total of percents for all seasons

        if (detail.getPercentSpring() == null) {

            // Spring Season % is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentSpring",
                    "operatingDetail.percentSpring.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getPercentSpring() != 0) {

            seasons++;
            total += detail.getPercentSpring();
        }

        if (detail.getPercentSummer() == null) {

            // Summer Season % is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentSummer",
                    "operatingDetail.percentSummer.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getPercentSummer() != 0) {

            seasons++;
            total += detail.getPercentSummer();
        }

        if (detail.getPercentFall() == null) {

            // Fall Season % is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentFall",
                    "operatingDetail.percentFall.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getPercentFall() != 0) {

            seasons++;
            total += detail.getPercentFall();
        }

        if (detail.getPercentWinter() == null) {

            // Winter Season % is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentWinter",
                    "operatingDetail.percentWinter.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));

        } else if (detail.getPercentWinter() != 0) {

            seasons++;
            total += detail.getPercentWinter();
        }

        if (detail.getAvgWeeksPerPeriod() == null) {
            
            // Average Weeks/Year is required
            result = false;
            context.addFederalError(
                    "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgWeeksPerPeriod",
                    "operatingDetail.avgWeeksPerPeriod.required", 
                    getEmissionsUnitIdentifier(detail),
                    getEmissionsProcessIdentifier(detail));
        } else {

            if (detail.getAvgWeeksPerPeriod() > 52 || detail.getAvgWeeksPerPeriod() < 1) {

                // Actual Weeks Per Year must be between 1 and 52
                result = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.actualHoursPerPeriod",
                        "operatingDetail.actualHoursPerPeriod.range", 
                        getEmissionsUnitIdentifier(detail),
                        getEmissionsProcessIdentifier(detail));
            }

            if (detail.getAvgWeeksPerPeriod() > 39 && seasons < 4) {

                // If Average Weeks/Year > 39 then all Season %'s are required to be > 0%
                result = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgWeeksPerPeriod",
                        "operatingDetail.avgWeeksPerPeriod.seasons.4", 
                        getEmissionsUnitIdentifier(detail),
                        getEmissionsProcessIdentifier(detail));

            }  if (detail.getAvgWeeksPerPeriod() > 26 && seasons < 3) {

                // If Average Weeks/Year > 26 then at least three Season %'s are required to be > 0%
                result = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgWeeksPerPeriod",
                        "operatingDetail.avgWeeksPerPeriod.seasons.3", 
                        getEmissionsUnitIdentifier(detail),
                        getEmissionsProcessIdentifier(detail));

            }  if (detail.getAvgWeeksPerPeriod() > 13 && seasons < 2) {

                // If Average Weeks/Year > 13 then at least two Season %'s are required to be > 0%
                result = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgWeeksPerPeriod",
                        "operatingDetail.avgWeeksPerPeriod.seasons.2", 
                        getEmissionsUnitIdentifier(detail),
                        getEmissionsProcessIdentifier(detail));

            }

            if (total > 100.5 || total < 99.5) {

                // The sum of seasonal percentages must be between 99.5 and 100.5
                result = false;
                context.addFederalError(
                        "report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percents",
                        "operatingDetail.percent.total", 
                        getEmissionsUnitIdentifier(detail),
                        getEmissionsProcessIdentifier(detail));
            }
        }


        return result;
    }

    private String getEmissionsUnitIdentifier(OperatingDetail source) {
        if (source.getReportingPeriod() != null && source.getReportingPeriod().getEmissionsProcess() != null 
                && source.getReportingPeriod().getEmissionsProcess().getEmissionsUnit() != null) {
            return source.getReportingPeriod().getEmissionsProcess().getEmissionsUnit().getUnitIdentifier();
        }
        return null;
    }

    private String getEmissionsProcessIdentifier(OperatingDetail source) {
        if (source.getReportingPeriod() != null && source.getReportingPeriod().getEmissionsProcess() != null) {
            return source.getReportingPeriod().getEmissionsProcess().getEmissionsProcessIdentifier();
        }
        return null;
    }
}
