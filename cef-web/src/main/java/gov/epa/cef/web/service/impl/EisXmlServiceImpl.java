package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.exception.AppValidationException;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.util.DateUtils;
import gov.epa.cef.web.util.SLTConfigHelper;
import net.exchangenetwork.schema.header._2.DocumentHeaderType;
import net.exchangenetwork.schema.header._2.DocumentPayloadType;
import net.exchangenetwork.schema.header._2.ExchangeNetworkDocumentType;
import net.exchangenetwork.schema.header._2.NameValuePair;
import net.exchangenetwork.schema.header._2.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class EisXmlServiceImpl {

    private static final String DataCategoryPropertyName = "DataCategory";

    private static final String DataflowName = "EIS_v1_0";

    private static final String DocumentTitle = "EIS";

    private static final String SubmissionTypePropertyName = "SubmissionType";

    private final CersXmlServiceImpl cersXmlService;

    private final SLTConfigHelper sltConfigHelper;

    private final CefConfig cefConfig;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EisXmlServiceImpl(CersXmlServiceImpl cersXmlService, SLTConfigHelper sltConfigHelper, CefConfig cefConfig) {

        this.cersXmlService = cersXmlService;
        this.sltConfigHelper = sltConfigHelper;
        this.cefConfig = cefConfig;
    }

    public ExchangeNetworkDocumentType generateEisDocument(EisHeaderDto eisHeader) {

        if (cefConfig.getFeatureCersV2Enabled()) {
            return generateEisDocumentV2_0(eisHeader);
        } else {
            return generateEisDocumentV1_2(eisHeader);
        }
    }

    private ExchangeNetworkDocumentType generateEisDocumentV1_2(EisHeaderDto eisHeader) {

        SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(eisHeader.getProgramSystemCode());

        net.exchangenetwork.schema.cer._1._2.CERSDataType cersData = new net.exchangenetwork.schema.cer._1._2.CERSDataType();
        cersData.setUserIdentifier(sltConfig.getSltEisUser());
        cersData.setProgramSystemCode(sltConfig.getSltEisProgramCode());

        Set<XMLGregorianCalendar> reportYears = new HashSet<>();

        eisHeader.getEmissionsReports().forEach(reportId -> {

            net.exchangenetwork.schema.cer._1._2.CERSDataType reportCersData = this.cersXmlService.generateCersData(reportId, eisHeader.getSubmissionStatus());
            cersData.getFacilitySite().addAll(reportCersData.getFacilitySite());

            cersData.setEmissionsYear(reportCersData.getEmissionsYear());
            reportYears.add(reportCersData.getEmissionsYear());
        });

        if (reportYears.size() > 1) {
            throw new AppValidationException("All reports for a single submission to EIS must be for the same year.");
        }

        net.exchangenetwork.schema.cer._1._2.ObjectFactory cersObjectFactory =
            new net.exchangenetwork.schema.cer._1._2.ObjectFactory();

        try {

            JAXBContext jaxbContext =
                JAXBContext.newInstance(net.exchangenetwork.schema.cer._1._2.CERSDataType.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // create an xml document to marshal CERS xml into so that the CERS namespace declaration will be at the CERS level for EIS
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            jaxbMarshaller.marshal( cersObjectFactory.createCERS(cersData), doc );

            return new ExchangeNetworkDocumentType()
                    .withId("_".concat(UUID.randomUUID().toString()))
                    .withHeader(
                        new DocumentHeaderType()
                            .withDataFlowName(DataflowName)
                            .withAuthorName(eisHeader.getAuthorName())
                            .withOrganizationName(eisHeader.getOrganizationName())
                            .withDocumentTitle(DocumentTitle)
                            .withProperty(
                                new NameValuePair()
                                    .withPropertyName(SubmissionTypePropertyName)
                                    .withPropertyValue(eisHeader.getSubmissionStatus().submissionType()),
                                new NameValuePair()
                                    .withPropertyName(DataCategoryPropertyName)
                                    .withPropertyValue(eisHeader.getSubmissionStatus().dataCategory()))
                            .withCreationDateTime(DateUtils.createGregorianCalendar()))
                    .withPayload(
                        new DocumentPayloadType()
                            .withId("_".concat(UUID.randomUUID().toString()))
                            .withAny(doc.getDocumentElement()));

        } catch (JAXBException | ParserConfigurationException e) {

            throw new IllegalStateException(e);
        }
    }

    private ExchangeNetworkDocumentType generateEisDocumentV2_0(EisHeaderDto eisHeader) {

        SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(eisHeader.getProgramSystemCode());

        net.exchangenetwork.schema.cer._2._0.CERSDataType cersData = new net.exchangenetwork.schema.cer._2._0.CERSDataType();
        cersData.setUserIdentifier(sltConfig.getSltEisUser());
        cersData.setProgramSystemCode(sltConfig.getSltEisProgramCode());

        Set<XMLGregorianCalendar> reportYears = new HashSet<>();

        eisHeader.getEmissionsReports().forEach(reportId -> {

            net.exchangenetwork.schema.cer._2._0.CERSDataType reportCersData = this.cersXmlService.generateCersV2Data(reportId, eisHeader.getSubmissionStatus());
            cersData.getFacilitySite().addAll(reportCersData.getFacilitySite());

            cersData.setEmissionsYear(reportCersData.getEmissionsYear());
            reportYears.add(reportCersData.getEmissionsYear());
        });

        if (reportYears.size() > 1) {
            throw new AppValidationException("All reports for a single submission to EIS must be for the same year.");
        }

        net.exchangenetwork.schema.cer._2._0.ObjectFactory cersObjectFactory =
            new net.exchangenetwork.schema.cer._2._0.ObjectFactory();

        try {

            JAXBContext jaxbContext =
                JAXBContext.newInstance(net.exchangenetwork.schema.cer._2._0.CERSDataType.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // create an xml document to marshal CERS xml into so that the CERS namespace declaration will be at the CERS level for EIS
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            jaxbMarshaller.marshal( cersObjectFactory.createCERS(cersData), doc );

            return new ExchangeNetworkDocumentType()
                    .withId("_".concat(UUID.randomUUID().toString()))
                    .withHeader(
                        new DocumentHeaderType()
                            .withDataFlowName(DataflowName)
                            .withAuthorName(eisHeader.getAuthorName())
                            .withOrganizationName(eisHeader.getOrganizationName())
                            .withDocumentTitle(DocumentTitle)
                            .withProperty(
                                new NameValuePair()
                                    .withPropertyName(SubmissionTypePropertyName)
                                    .withPropertyValue(eisHeader.getSubmissionStatus().submissionType()),
                                new NameValuePair()
                                    .withPropertyName(DataCategoryPropertyName)
                                    .withPropertyValue(eisHeader.getSubmissionStatus().dataCategory()))
                            .withCreationDateTime(DateUtils.createGregorianCalendar()))
                    .withPayload(
                        new DocumentPayloadType()
                            .withId("_".concat(UUID.randomUUID().toString()))
                            .withAny(doc.getDocumentElement()));

        } catch (JAXBException | ParserConfigurationException e) {

            throw new IllegalStateException(e);
        }
    }

    public void writeEisXmlTo(EisHeaderDto eisHeader, OutputStream outputStream) {

        ExchangeNetworkDocumentType result = generateEisDocument(eisHeader);

       writeEisXmlTo(result, outputStream);
    }

    public void writeEisXmlTo(ExchangeNetworkDocumentType eisDoc, OutputStream outputStream) {

        try {
            ObjectFactory objectFactory = new ObjectFactory();

            JAXBContext jaxbContext =
                JAXBContext.newInstance(ExchangeNetworkDocumentType.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(objectFactory.createDocument(eisDoc), outputStream);

        } catch (JAXBException e) {

            throw new IllegalStateException(e);
        }
    }
}
