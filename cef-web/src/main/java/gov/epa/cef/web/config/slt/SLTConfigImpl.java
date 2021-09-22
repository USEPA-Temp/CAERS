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
package gov.epa.cef.web.config.slt;

import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.config.SLTPropertyName;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;

public class SLTConfigImpl implements SLTBaseConfig {

    private final String programSystemCode;

    private final SLTPropertyProvider propertyProvider;

    public SLTConfigImpl(String programSystemCode, SLTPropertyProvider propertyProvider) {

        super();

        this.programSystemCode = programSystemCode;
        this.propertyProvider = propertyProvider;

    }

    public String getSltEmail() {
        return this.propertyProvider.getString(SLTPropertyName.EmailAddress, programSystemCode);
    }

    public String getSltEisUser() {
        return this.propertyProvider.getString(SLTPropertyName.EisUser, programSystemCode);
    }

    public String getSltEisProgramCode() {
        return this.propertyProvider.getString(SLTPropertyName.EisProgramCode, programSystemCode);
    }
    
    public Boolean getFacilityNaicsEnabled() {
    	 return this.propertyProvider.getBoolean(SLTPropertyName.FacilityNaicsEnabled, programSystemCode);
    }
}
