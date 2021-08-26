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

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *  PointSourceSccCode entity.
 */
@Entity
@Table(name = "point_source_scc_code")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PointSourceSccCode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//Fields

  @Id
  @Column(name = "code", unique = true, nullable = false, precision = 10, scale = 0)
  private String code;

  @Column(name = "last_inventory_year")
  private Short lastInventoryYear;
  
  @Column(name = "fuel_use_required", nullable = false)
  private Boolean fuelUseRequired;

  // Property accessors

  public String getCode() {
      return this.code;
  }

  public void setCode(String code) {
      this.code = code;
  }

  public Short getLastInventoryYear() {
      return this.lastInventoryYear;
  }

  public void setLastInventoryYear(Short lastInventoryYear) {
      this.lastInventoryYear = lastInventoryYear;
  }
  
  public Boolean getFuelUseRequired() {
	  return fuelUseRequired;
  }
  
  public void setFuelUseRequired(Boolean fuelUseRequired) {
	  this.fuelUseRequired = fuelUseRequired;
  }

}
