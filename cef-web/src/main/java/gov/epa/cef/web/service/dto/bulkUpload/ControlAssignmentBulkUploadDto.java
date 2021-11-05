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

import gov.epa.cef.web.annotation.CsvColumn;
import gov.epa.cef.web.annotation.CsvFileName;

import java.io.Serializable;

@CsvFileName(name = "control_assignments.csv")
public class ControlAssignmentBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long controlId;

    private Long controlPathChildId;

    @NotNull(message = "Control Path ID is required.")
    private Long controlPathId;

    private String pathName;
    
    private String childPathName;
    
    private String controlName;

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

    @CsvColumn(name = "Control ID", order = 3)
    public Long getControlId() {

        return controlId;
    }

    public void setControlId(Long controlId) {

        this.controlId = controlId;
    }

    @CsvColumn(name = "Control Path Child ID", order = 4)
    public Long getControlPathChildId() {

        return controlPathChildId;
    }

    public void setControlPathChildId(Long controlPathChildId) {

        this.controlPathChildId = controlPathChildId;
    }

    @CsvColumn(name = "Control Path ID", order = 2)
    public Long getControlPathId() {

        return controlPathId;
    }

    public void setControlPathId(Long controlPathId) {

        this.controlPathId = controlPathId;
    }


    @CsvColumn(name = "Path Name", order = 2)
    public String getPathName() {
    	return pathName;
    }
    public void setPathName(String pathName) {
    	this.pathName = pathName;
    }


    @CsvColumn(name = "Child Path Name", order = 4)
    public String getChildPathName() {
    	return childPathName;
    }
    public void setChildPathName(String childPathName) {
    	this.childPathName = childPathName;
    }


    @CsvColumn(name = "Control Name", order = 3)
    public String getControlName() {
    	return controlName;
    }
    public void setControlName(String controlName) {
    	this.controlName = controlName;
    }
    

    @CsvColumn(name = "ID", order = 1)
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @CsvColumn(name = "Percent Apportionment", order = 6)
    public String getPercentApportionment() {

        return percentApportionment;
    }

    public void setPercentApportionment(String percentApportionment) {

        this.percentApportionment = percentApportionment;
    }

    @CsvColumn(name = "Sequence Number", order = 5)
    public String getSequenceNumber() {

        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {

        this.sequenceNumber = sequenceNumber;
    }

}
