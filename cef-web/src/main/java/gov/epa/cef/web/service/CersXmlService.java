package gov.epa.cef.web.service;

import net.exchangenetwork.schema.cer._1._2.CERSDataType;

import java.io.OutputStream;

import gov.epa.cef.web.service.dto.EisSubmissionStatus;

public interface CersXmlService {

    /**
     * Generate the CERS V2.0 XML classes for the specified emissions report
     * @param reportId
     * @return
     */
    net.exchangenetwork.schema.cer._2._0.CERSDataType generateCersV2Data(Long reportId, EisSubmissionStatus submissionStatus);

    /**
     * Generate the CERS V1.2 XML classes for the specified emissions report
     * @param reportId
     * @return
     */
    CERSDataType generateCersData(Long reportId, EisSubmissionStatus submissionStatus);

    /**
     * Generate XML from the CERS XML V2.0 classes for the specified emissions report
     * @param reportId
     * @param oututStream
     * @param submissionType
     * @return
     */
    void writeCersV2XmlTo(long reportId, OutputStream outputStream, EisSubmissionStatus submissionStatus);

    /**
     * Generate XML from the CERS XML V1.2 classes for the specified emissions report
     * @param reportId
     * @param oututStream
     * @param submissionType
     * @return
     */
    void writeCersXmlTo(long reportId, OutputStream outputStream, EisSubmissionStatus submissionStatus);
}
