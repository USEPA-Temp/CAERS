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
import java.io.Serializable;

public class ReleasePointApptBulkUploadDto extends BaseWorksheetDto implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Release Point Apportionment ID is required.")
    private Long id;

    @NotNull(message = "Release Point ID is required.")
    private Long releasePointId;

    @NotNull(message = "Emission Process ID is required.")
    private Long emissionProcessId;

    @NotBlank(message = "Percent Apportionment is required.")
    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,2})?$",
        message = "Percent is not in expected numeric format: '{3}.{2}' digits; found '${validatedValue}'.")
    private String percent;

    private Long controlPathId;

    public ReleasePointApptBulkUploadDto() {

        super(WorksheetName.ReleasePointAppt);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getReleasePointId() {
        return releasePointId;
    }
    public void setReleasePointId(Long releasePointId) {
        this.releasePointId = releasePointId;
    }

    public Long getEmissionProcessId() {
        return emissionProcessId;
    }
    public void setEmissionProcessId(Long emissionProcessId) {
        this.emissionProcessId = emissionProcessId;
    }

    public String getPercent() {
        return percent;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Long getControlPathId() {
        return controlPathId;
    }
    public void setControlPathId(Long controlPathId) {
        this.controlPathId = controlPathId;
    }
}
