package gov.epa.cef.web.service.impl;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.mapper.cers.CersDataTypeMapper;
import gov.epa.cef.web.service.mapper.cers.CersFacilitySiteMapper;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;
import net.exchangenetwork.schema.cer._1._2.FacilitySiteDataType;
import net.exchangenetwork.schema.cer._1._2.ObjectFactory;

@Service
public class CersXmlServiceImpl implements CersXmlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CersXmlServiceImpl.class);

    @Autowired
    private FacilitySiteRepository facSiteRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CersDataTypeMapper cersMapper;

    @Autowired
    private CersFacilitySiteMapper siteMapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#generateCersData(java.lang.Long)
     */
    @Override
    public CERSDataType generateCersData(Long facilitySiteId) {

        FacilitySite source = facSiteRepo.findById(facilitySiteId).orElse(null);

        CERSDataType cers = cersMapper.fromFacilitySite(source);
        FacilitySiteDataType site = siteMapper.fromFacilitySite(source);
        cers.getFacilitySite().add(site);

        cers.setUserIdentifier(userService.getCurrentUser().getEmail());

        return cers;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#retrieveCersXml(java.lang.Long)
     */
    @Override
    public String retrieveCersXml(Long facilitySiteId) {

        CERSDataType cers = generateCersData(facilitySiteId);

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
