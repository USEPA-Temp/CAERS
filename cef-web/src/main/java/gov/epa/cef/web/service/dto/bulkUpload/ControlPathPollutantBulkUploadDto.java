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
package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ControlPathPollutantBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Control Path Pollutant ID is required.")
    private Long id;

    @NotNull(message = "Control Path ID is required.")
    private Long controlPathId;

    @NotBlank(message = "Pollutant Code is required.")
    @Size(max = 12, message = "Pollutant Code can not exceed {max} chars; found '${validatedValue}'.")
    private String pollutantCode;

    @NotBlank(message = "Percent Reduction is required.")
    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1})?$",
        message = "Percent Reduction Efficiency is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentReduction;

    public ControlPathPollutantBulkUploadDto() {

        super(WorksheetName.ControlPathPollutant);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getControlPathId() {
        return controlPathId;
    }

    public void setControlPathId(Long controlPathId) {
        this.controlPathId = controlPathId;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutant) {
        this.pollutantCode = pollutant;
    }

    public String getPercentReduction() {
        return percentReduction;
    }

    public void setPercentReduction(String percentReduction) {
        this.percentReduction = percentReduction;
    }

}
