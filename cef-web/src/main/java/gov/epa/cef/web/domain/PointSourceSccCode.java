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
