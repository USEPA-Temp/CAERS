package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class FacilityNAICSDto implements Serializable{

        /**
         * default version id
         */
        private static final long serialVersionUID = 1L;
        
        private Long id;
        private Long facilitySiteId;
        private String code;
        private String description;
        private boolean primaryFlag;
        
        public Long getId() {
            return id;
        }
        public Long getFacilitySiteId() {
            return facilitySiteId;
        }
        public String getCode() {
            return code;
        }
        public String getDescription() {
            return description;
        }
        public boolean isPrimaryFlag() {
            return primaryFlag;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public void setFacilitySiteId(Long facilitySiteId) {
            this.facilitySiteId = facilitySiteId;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setPrimaryFlag(boolean primaryFlag) {
            this.primaryFlag = primaryFlag;
        }
        
        public FacilityNAICSDto withId(Long id) {
        	setId(id);
        	return this;
        }
        
}
