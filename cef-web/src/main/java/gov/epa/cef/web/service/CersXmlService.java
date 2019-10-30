package gov.epa.cef.web.service;

import net.exchangenetwork.schema.cer._1._2.CERSDataType;

public interface CersXmlService {

    /**
     * Generate the CERS XML classes for the specified emissions report
     * @param reportId
     * @return
     */
    CERSDataType generateCersData(Long reportId);

    /**
     * Generate XML from the CERS XML classes for the specified emissions report
     * @param reportId
     * @return
     */
    byte[] retrieveCersXml(Long reportId);

}
