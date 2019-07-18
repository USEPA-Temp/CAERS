package gov.epa.cef.web.service.impl;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.mapper.cers.CersDataTypeMapper;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;
import net.exchangenetwork.schema.cer._1._2.ObjectFactory;

@Service
public class CersXmlServiceImpl implements CersXmlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CersXmlServiceImpl.class);

    @Autowired
    private EmissionsReportRepository reportRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CersDataTypeMapper cersMapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#generateCersData(java.lang.Long)
     */
    @Override
    public CERSDataType generateCersData(Long reportId) {

        EmissionsReport source = reportRepo.findById(reportId).orElse(null);

        CERSDataType cers = cersMapper.fromEmissionsReport(source);
        // TODO: find out if programSystemCode should always be the same at the facilitySite and report level
        // and if this needs to be added at the report level or moved there instead
        if (!cers.getFacilitySite().isEmpty()) {
            cers.setProgramSystemCode(cers.getFacilitySite().get(0).getFacilityIdentification().get(0).getProgramSystemCode());
        }

        cers.setUserIdentifier(userService.getCurrentUser().getEmail());

        return cers;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#retrieveCersXml(java.lang.Long)
     */
    @Override
    public String retrieveCersXml(Long reportId) {

        CERSDataType cers = generateCersData(reportId);

        try {
            ObjectFactory objectFactory = new ObjectFactory();
            JAXBContext jaxbContext = JAXBContext.newInstance( CERSDataType.class );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(objectFactory.createCERS(cers), sw);

            return sw.toString();
        } catch (JAXBException e) {
            LOGGER.error("error while marshalling", e);
            throw ApplicationException.asApplicationException(e);
        }
    }

}
