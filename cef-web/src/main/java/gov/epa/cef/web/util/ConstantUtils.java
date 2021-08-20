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
package gov.epa.cef.web.util;

public class ConstantUtils {
	
	//OPERATING STATUSES
	public static final String STATUS_OPERATING = "OP";
	public static final String STATUS_PERMANENTLY_SHUTDOWN = "PS";
	public static final String STATUS_TEMPORARILY_SHUTDOWN = "TS";
	
	// RELEASE POINT TYPE CODES
	public static final String FUGITIVE_RELEASE_POINT_TYPE = "1";
	
	//EIS TRANSMISSION TYPES
	public static final String EIS_TRANSMISSION_FACILITY_INVENTORY = "FacilityInventory";
	public static final String EIS_TRANSMISSION_POINT_EMISSIONS = "Point";
	
	//LANDFILL FACILITY SOURCE TYPE CODE
	public static final String FACILITY_SOURCE_LANDFILL_CODE = "104";
	
	//NUMBER PATTERNS
	public static final String REGEX_ONE_DECIMAL_PRECISION = "^\\d{0,3}(\\.\\d{1})?$";
	
}
