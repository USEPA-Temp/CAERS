package gov.epa.cef.web.domain;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 *  EisLatLongToleranceLookup entity.
 */
@Entity
@Table(name = "eis_latlong_tolerance_lookup")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EisLatLongToleranceLookup implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		
		//Fields
		
		@Id
		@Column(name = "eis_program_id", unique = true, nullable = false, length = 22)
		private String eisProgramId;
		
		@Column(name = "coordinate_tolerance", precision = 10, scale = 6)
		private BigDecimal coordinateTolerance;
		
		// Property accessors

		public String getEisProgramId() {
			return eisProgramId;
		}

		public void setEisProgramId(String eisProgramId) {
			this.eisProgramId = eisProgramId;
		}

		public BigDecimal getCoordinateTolerance() {
			return coordinateTolerance;
		}

		public void setCoordinateTolerance(BigDecimal coordinateTolerance) {
			this.coordinateTolerance = coordinateTolerance;
		}

}
