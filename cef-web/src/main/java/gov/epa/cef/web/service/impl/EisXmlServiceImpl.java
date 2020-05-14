package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.util.DateUtils;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;
import net.exchangenetwork.schema.header._2.DocumentHeaderType;
import net.exchangenetwork.schema.header._2.DocumentPayloadType;
import net.exchangenetwork.schema.header._2.ExchangeNetworkDocumentType;
import net.exchangenetwork.schema.header._2.NameValuePair;
import net.exchangenetwork.schema.header._2.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class EisXmlServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DataflowName = "EIS_v1_0";

    private static final String DocumentTitle = "EIS";

    private static final String SubmissionTypePropertyName = "SubmissionType";

    private static final String DataCategoryPropertyName = "DataCategory";

    private final CersXmlServiceImpl cersXmlService;

    @Autowired
    EisXmlServiceImpl(CersXmlServiceImpl cersXmlService) {

        this.cersXmlService = cersXmlService;
    }

    public ExchangeNetworkDocumentType generateEisDocument(EisHeaderDto eisHeader) {

        CERSDataType cersData = new CERSDataType();

        eisHeader.getEmissionReports().forEach(reportId -> {

            CERSDataType reportCersData = this.cersXmlService.generateCersData(reportId);
            cersData.getFacilitySite().addAll(reportCersData.getFacilitySite());
        });

        net.exchangenetwork.schema.cer._1._2.ObjectFactory cersObjectFactory =
            new net.exchangenetwork.schema.cer._1._2.ObjectFactory();

        return new ExchangeNetworkDocumentType()
            .withId(UUID.randomUUID().toString())
            .withHeader(
                new DocumentHeaderType()
                    .withDataFlowName(DataflowName)
                    .withDocumentTitle(DocumentTitle)
                    .withProperty(
                        new NameValuePair()
                            .withPropertyName(SubmissionTypePropertyName)
                            .withPropertyValue(eisHeader.getSubmissionType().name()),
                        new NameValuePair()
                            .withPropertyName(DataCategoryPropertyName)
                            .withPropertyValue(eisHeader.getDataCategory().name()))
                    .withCreationDateTime(DateUtils.createGregorianCalendar()))
            .withPayload(
                new DocumentPayloadType()
                    .withId(UUID.randomUUID().toString())
                    .withAny(cersObjectFactory.createCERS(cersData)));
    }

    public void writeEisXmlTo(EisHeaderDto eisHeader, OutputStream outputStream) {

        ExchangeNetworkDocumentType result = generateEisDocument(eisHeader);

        try {
            ObjectFactory objectFactory = new ObjectFactory();

            JAXBContext jaxbContext =
                JAXBContext.newInstance(ExchangeNetworkDocumentType.class, CERSDataType.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(objectFactory.createDocument(result), outputStream);

        } catch (JAXBException e) {

            throw new IllegalStateException(e);
        }
    }
}
