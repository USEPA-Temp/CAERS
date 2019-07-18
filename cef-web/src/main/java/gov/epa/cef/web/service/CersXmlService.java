package gov.epa.cef.web.service;

import net.exchangenetwork.schema.cer._1._2.CERSDataType;

public interface CersXmlService {

    /**
     * Generate the CERS XML classes for the specified facilitySite
     * @param facilitySiteId
     * @return
     */
    CERSDataType generateCersData(Long facilitySiteId);

    /**
     * Generate XML from the CERS XML classes for the specified facilitySite
     * @param facilitySiteId
     * @return
     */
    String retrieveCersXml(Long facilitySiteId);

}