package gov.epa.cef.web.service;

import net.exchangenetwork.schema.cer._1._2.CERSDataType;

import java.io.OutputStream;

import gov.epa.cef.web.service.dto.EisSubmissionStatus;

public interface CersXmlService {

    /**
     * Generate the CERS XML classes for the specified emissions report
     * @param reportId
     * @return
     */
    CERSDataType generateCersData(Long reportId, EisSubmissionStatus submissionStatus);

    /**
     * Generate XML from the CERS XML classes for the specified emissions report
     * @param reportId
     * @return
     */
    void writeCersXmlTo(long reportId, OutputStream outputStream);
}
