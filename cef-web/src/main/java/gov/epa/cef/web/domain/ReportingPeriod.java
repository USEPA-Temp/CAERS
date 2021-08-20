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
package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * ReportingPeriod entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reporting_period")

public class ReportingPeriod extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_process_id", nullable = false)
    private EmissionsProcess emissionsProcess;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_period_type_code", nullable = false)
    private ReportingPeriodCode reportingPeriodTypeCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_operating_type_code", nullable = false)
    private EmissionsOperatingTypeCode emissionsOperatingTypeCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_parameter_type_code", nullable = false)
    private CalculationParameterTypeCode calculationParameterTypeCode;
    
    @Column(name = "calculation_parameter_value", nullable = false, precision = 131089, scale = 0)
    private BigDecimal calculationParameterValue;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_parameter_uom", nullable = false)
    private UnitMeasureCode calculationParameterUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_material_code", nullable = false)
    private CalculationMaterialCode calculationMaterialCode;
    
    @Column(name = "fuel_use_value", nullable = false, precision = 131089, scale = 0)
    private BigDecimal fuelUseValue;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_use_uom", nullable = false)
    private UnitMeasureCode fuelUseUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_use_material_code", nullable = false)
    private CalculationMaterialCode fuelUseMaterialCode;
    
    @Column(name = "heat_content_value", nullable = false, precision = 131089, scale = 0)
    private BigDecimal heatContentValue;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heat_content_uom", nullable = false)
    private UnitMeasureCode heatContentUom;
    
    @Column(name = "comments", length = 400)
    private String comments;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportingPeriod")
    private List<Emission> emissions = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportingPeriod")
    private List<OperatingDetail> operatingDetails = new ArrayList<>();

    
    /***
     * Default constructor
     */
    public ReportingPeriod() {}
    
    
    /***
     * Copy constructor
     * @param process
     * @param originalReportingPeriod
     */
    public ReportingPeriod(EmissionsProcess process, ReportingPeriod originalReportingPeriod) {
		this.id = originalReportingPeriod.getId();
        this.emissionsProcess = process;
        this.reportingPeriodTypeCode = originalReportingPeriod.getReportingPeriodTypeCode();
        this.emissionsOperatingTypeCode = originalReportingPeriod.getEmissionsOperatingTypeCode();
        this.calculationParameterTypeCode = originalReportingPeriod.getCalculationParameterTypeCode();
        this.calculationParameterValue = originalReportingPeriod.getCalculationParameterValue();
        this.calculationParameterUom = originalReportingPeriod.getCalculationParameterUom();
        this.calculationMaterialCode = originalReportingPeriod.getCalculationMaterialCode();
        this.fuelUseValue = originalReportingPeriod.getFuelUseValue();
        this.fuelUseUom = originalReportingPeriod.getFuelUseUom();
        this.fuelUseMaterialCode = originalReportingPeriod.getFuelUseMaterialCode();
        this.heatContentValue = originalReportingPeriod.getHeatContentValue();
        this.heatContentUom = originalReportingPeriod.getHeatContentUom();
        this.comments = originalReportingPeriod.getComments();

        for (Emission emission : originalReportingPeriod.getEmissions()) {
        	this.emissions.add(new Emission(this, emission));
        }
        for (OperatingDetail operatingDetail : originalReportingPeriod.getOperatingDetails()) {
        	this.operatingDetails.add(new OperatingDetail(this, operatingDetail));
        }
    }
    
    public EmissionsProcess getEmissionsProcess() {
        return this.emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcess emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    public ReportingPeriodCode getReportingPeriodTypeCode() {
        return this.reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(ReportingPeriodCode reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public EmissionsOperatingTypeCode getEmissionsOperatingTypeCode() {
        return this.emissionsOperatingTypeCode;
    }

    public void setEmissionsOperatingTypeCode(EmissionsOperatingTypeCode emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    public CalculationParameterTypeCode getCalculationParameterTypeCode() {
        return this.calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(CalculationParameterTypeCode calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public BigDecimal getCalculationParameterValue() {
        return this.calculationParameterValue;
    }

    public void setCalculationParameterValue(BigDecimal calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    public UnitMeasureCode getCalculationParameterUom() {
        return this.calculationParameterUom;
    }

    public void setCalculationParameterUom(UnitMeasureCode calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    public CalculationMaterialCode getCalculationMaterialCode() {
        return this.calculationMaterialCode;
    }

    public void setCalculationMaterialCode(CalculationMaterialCode calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public BigDecimal getFuelUseValue() {
		return fuelUseValue;
	}

	public void setFuelUseValue(BigDecimal fuelUseValue) {
		this.fuelUseValue = fuelUseValue;
	}

	public UnitMeasureCode getFuelUseUom() {
		return fuelUseUom;
	}

	public void setFuelUseUom(UnitMeasureCode fuelUseUom) {
		this.fuelUseUom = fuelUseUom;
	}

	public CalculationMaterialCode getFuelUseMaterialCode() {
		return fuelUseMaterialCode;
	}

	public void setFuelUseMaterialCode(
			CalculationMaterialCode fuelUseMaterialCode) {
		this.fuelUseMaterialCode = fuelUseMaterialCode;
	}

	public BigDecimal getHeatContentValue() {
		return heatContentValue;
	}

	public void setHeatContentValue(BigDecimal heatContentValue) {
		this.heatContentValue = heatContentValue;
	}

	public UnitMeasureCode getHeatContentUom() {
		return heatContentUom;
	}

	public void setHeatContentUom(UnitMeasureCode heatContentUom) {
		this.heatContentUom = heatContentUom;
	}

	public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Emission> getEmissions() {
        return this.emissions;
    }

    public void setEmissions(List<Emission> emissions) {

        this.emissions.clear();
        if (emissions != null) {
            this.emissions.addAll(emissions);
        }
    }

    public List<OperatingDetail> getOperatingDetails() {
        return this.operatingDetails;
    }

    public void setOperatingDetails(List<OperatingDetail> operatingDetails) {

        this.operatingDetails.clear();
        if (operatingDetails != null) {
            this.operatingDetails.addAll(operatingDetails);
        }
    }


    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;

		for (Emission emission : this.emissions) {
			emission.clearId();
		}
		for (OperatingDetail operatingDetail : this.operatingDetails) {
			operatingDetail.clearId();
		}
    }

}