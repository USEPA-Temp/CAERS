package gov.epa.cef.web.service.mapper.cers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportingPeriod;
import net.exchangenetwork.schema.cer._1._2.EmissionsDataType;
import net.exchangenetwork.schema.cer._1._2.EmissionsUnitDataType;
import net.exchangenetwork.schema.cer._1._2.IdentificationDataType;
import net.exchangenetwork.schema.cer._1._2.OperatingDetailsDataType;
import net.exchangenetwork.schema.cer._1._2.ProcessDataType;
import net.exchangenetwork.schema.cer._1._2.ReleasePointApportionmentDataType;
import net.exchangenetwork.schema.cer._1._2.ReportingPeriodDataType;

@Mapper(componentModel = "spring", uses = {CersReleasePointMapper.class})
public interface CersEmissionsUnitMapper {

    @Mapping(source="description", target="unitDescription")
    @Mapping(source="unitTypeCode.code", target="unitTypeCode")
    @Mapping(source="designCapacity", target="unitDesignCapacity")
    @Mapping(source="unitOfMeasureCode.code", target="unitDesignCapacityUnitofMeasureCode")
    @Mapping(source="operatingStatusCode.code", target="unitStatusCode")
    @Mapping(source="statusYear", target="unitStatusCodeYear")
    @Mapping(source="comments", target="unitComment")
    @Mapping(source=".", target="unitIdentification")
    @Mapping(source="emissionsProcesses", target="unitEmissionsProcess")
//    @Mapping(source="", target="unitControlApproach")
    EmissionsUnitDataType fromEmissionsUnit(EmissionsUnit source);

    @Mapping(source="sccCode", target="sourceClassificationCode")
    @Mapping(source="aircraftEngineTypeCode.code", target="aircraftEngineTypeCode")
    @Mapping(source="description", target="processDescription")
    @Mapping(source="comments", target="processComment")
    @Mapping(source=".", target="processIdentification")
    @Mapping(source="reportingPeriods", target="reportingPeriod")
    @Mapping(source="releasePointAppts", target="releasePointApportionment")
    ProcessDataType processFromEmissionsProcess(EmissionsProcess source);

    @Mapping(source="percent", target="averagePercentEmissions")
//    @Mapping(source="", target="releasePointApportionmentComment")
    @Mapping(source="releasePoint", target="releasePointApportionmentIdentification")
    ReleasePointApportionmentDataType rpApptFromReleasePointAppt(ReleasePointAppt source);

    @Mapping(source="reportingPeriodTypeCode.code", target="reportingPeriodTypeCode")
    @Mapping(source="emissionsOperatingTypeCode.code", target="emissionOperatingTypeCode")
    @Mapping(source="calculationParameterTypeCode.code", target="calculationParameterTypeCode")
    @Mapping(source="calculationParameterValue", target="calculationParameterValue")
    @Mapping(source="calculationParameterUom.code", target="calculationParameterUnitofMeasure")
    @Mapping(source="calculationMaterialCode.code", target="calculationMaterialCode")
    @Mapping(source="comments", target="reportingPeriodComment")
    @Mapping(source="operatingDetails", target="operatingDetails")
    @Mapping(source="emissions", target="reportingPeriodEmissions")
    ReportingPeriodDataType periodFromReportingPeriod(ReportingPeriod source);

    @Mapping(source="actualHoursPerPeriod", target="actualHoursPerPeriod")
    @Mapping(source="avgDaysPerWeek", target="averageDaysPerWeek")
    @Mapping(source="avgHoursPerDay", target="averageHoursPerDay")
    @Mapping(source="avgWeeksPerPeriod", target="averageWeeksPerPeriod")
    @Mapping(source="percentWinter", target="percentWinterActivity")
    @Mapping(source="percentSpring", target="percentSpringActivity")
    @Mapping(source="percentSummer", target="percentSummerActivity")
    @Mapping(source="percentFall", target="percentFallActivity")
    OperatingDetailsDataType operatingDetailsFromOperatingDetail(OperatingDetail source);

    @Mapping(source="pollutantCode", target="pollutantCode")
    @Mapping(source="totalEmissions", target="totalEmissions")
    @Mapping(source="emissionsUomCode.code", target="emissionsUnitofMeasureCode")
    @Mapping(source="emissionsFactor", target="emissionFactor")
    @Mapping(source="emissionsNumeratorUom.code", target="emissionFactorNumeratorUnitofMeasureCode")
    @Mapping(source="emissionsDenominatorUom.code", target="emissionFactorDenominatorUnitofMeasureCode")
    @Mapping(source="emissionsFactorText", target="emissionFactorText")
    @Mapping(source="emissionsCalcMethodCode.code", target="emissionCalculationMethodCode")
    @Mapping(source="comments", target="emissionsComment")
    EmissionsDataType emissionsFromEmission(Emission source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="unitIdentifier", target="identifier")
    @Mapping(source="programSystemCode", target="programSystemCode")
    IdentificationDataType identificationFromEmissionsUnit(EmissionsUnit source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="emissionsProcessIdentifier", target="identifier")
    @Mapping(source="emissionsUnit.programSystemCode", target="programSystemCode")
    IdentificationDataType identificationFromEmissionsProcess(EmissionsProcess source);

    // TODO: the XML appears to only support 1 operating detail per reporting period, might want to change our db schema
    default OperatingDetailsDataType operatingDetailsFromOperatingDetailList(Collection<OperatingDetail> source) {

        return operatingDetailsFromOperatingDetail(source.stream().findFirst().orElse(null));
    }

    default List<IdentificationDataType> identificationListFromEmissionsUnit(EmissionsUnit source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(identificationFromEmissionsUnit(source));
    }

    // TODO: the XML appears to support multiple process identifications per process and some examples contain multiple. Might want to change our db
    default List<IdentificationDataType> identificationListFromEmissionsProcess(EmissionsProcess source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(identificationFromEmissionsProcess(source));
    }

}
