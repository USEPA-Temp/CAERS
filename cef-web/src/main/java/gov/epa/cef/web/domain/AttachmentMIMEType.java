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

public enum AttachmentMIMEType {
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XLSX_CSV("application/vnd.ms-excel"),
    TXT("text/plain"),
    PDF("application/pdf"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    CSV("text/csv");
	
	private final String label;

	AttachmentMIMEType(String label) {
        this.label = label;
    }

    public String code() {
        return this.name();
    }

    public String label() {
        return this.label;
    }
    
    public static AttachmentMIMEType fromLabel(String label) {
        for (AttachmentMIMEType amt : AttachmentMIMEType.values()) {
            if (amt.label.equalsIgnoreCase(label)) {
                return amt;
            }
        }
        return null;
    }
}
