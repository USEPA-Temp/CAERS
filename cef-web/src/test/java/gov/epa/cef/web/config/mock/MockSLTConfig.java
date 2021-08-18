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
package gov.epa.cef.web.config.mock;

import gov.epa.cef.web.config.SLTBaseConfig;

public class MockSLTConfig implements SLTBaseConfig {

    private String sltEmail;
    private String sltEisUser;
    private String sltEisProgramCode;

    public String getSltEmail() {
        return sltEmail;
    }

    public void setSltEmail(String sltEmail) {
        this.sltEmail = sltEmail;
    }

    public String getSltEisUser() {
        return sltEisUser;
    }

    public void setSltEisUser(String sltEisUser) {
        this.sltEisUser = sltEisUser;
    }

    public String getSltEisProgramCode() {
        return sltEisProgramCode;
    }

    public void setSltEisProgramCode(String sltEisProgramCode) {
        this.sltEisProgramCode = sltEisProgramCode;
    }

}
