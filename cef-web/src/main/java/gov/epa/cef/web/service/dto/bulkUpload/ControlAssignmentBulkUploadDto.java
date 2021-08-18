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

public class ControlAssignmentBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long controlId;

    private Long controlPathChildId;

    @NotNull(message = "Control Path ID is required.")
    private Long controlPathId;

    @NotNull(message = "Control Assignment ID is required.")
    private Long id;

    @NotBlank(message = "Percent Apportionment is required.")
    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,2})?$",
        message = "Percent Apportionment is not in expected numeric format: '{3}.{2}' digits; found '${validatedValue}'.")
    private String percentApportionment;

    @NotBlank(message = "Sequence number is required.")
    @Pattern(regexp = PositiveIntPattern,
        message = "Sequence number is not in expected numeric format: '{10}' digits; found '${validatedValue}'.")
    private String sequenceNumber;

    public ControlAssignmentBulkUploadDto() {

        super(WorksheetName.ControlAssignment);
    }

    public Long getControlId() {

        return controlId;
    }

    public void setControlId(Long controlId) {

        this.controlId = controlId;
    }

    public Long getControlPathChildId() {

        return controlPathChildId;
    }

    public void setControlPathChildId(Long controlPathChildId) {

        this.controlPathChildId = controlPathChildId;
    }

    public Long getControlPathId() {

        return controlPathId;
    }

    public void setControlPathId(Long controlPathId) {

        this.controlPathId = controlPathId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getPercentApportionment() {

        return percentApportionment;
    }

    public void setPercentApportionment(String percentApportionment) {

        this.percentApportionment = percentApportionment;
    }

    public String getSequenceNumber() {

        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {

        this.sequenceNumber = sequenceNumber;
    }

}
