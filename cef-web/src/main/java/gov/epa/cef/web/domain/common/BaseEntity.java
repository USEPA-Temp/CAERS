package gov.epa.cef.web.domain.common;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
public abstract class BaseEntity implements Serializable, Comparable<BaseEntity> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement(name = "id")
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof BaseEntity && ((BaseEntity) o).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = (int) (31 * result + id);
		return result;
	}

	@Override
	public int compareTo(BaseEntity o) {
		if (this.id == null || o.id == null) {
			return 0;
		}
		return (int) (this.id - o.id);
	}

}
