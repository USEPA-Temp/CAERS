/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.Communication;
import gov.epa.cef.web.domain.Attachment;
import gov.epa.cef.web.domain.UserFacilityAssociation;
import gov.epa.cef.web.repository.CommunicationRepository;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.AttachmentRepository;
import gov.epa.cef.web.repository.UserFacilityAssociationRepository;
import gov.epa.cef.web.service.CommunicationService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.CommunicationDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.dto.UserFacilityAssociationDto;
import gov.epa.cef.web.service.mapper.CommunicationMapper;
import gov.epa.cef.web.util.SLTConfigHelper;

@Service
public class CommunicationServiceImpl implements CommunicationService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommunicationMapper commMapper;
	
	@Autowired
	private AttachmentRepository reportAttachmentsRepo;
	
	@Autowired
	private CommunicationRepository commRepo;
	
	@Autowired
	private UserFacilityAssociationRepository userFacilityRepo;
	
	@Autowired
	private ProgramSystemCodeRepository programSystemCodeRepo;
	
	@Autowired
	private UserFacilityAssociationServiceImpl userFacilityAssocService;
	
	@Autowired
    private NotificationService notificationService;
	
	@Autowired
    private SLTConfigHelper sltConfigHelper;
	
	@Autowired
    private CefConfig cefConfig;
	
	private final String PREPARER_ROLE = "Preparer";
	private final String NEI_CERTIFIER_ROLE = "NEI Certifier";
	
	/**
     * Send SLT Email Notification
     */
	public Communication sendNotification(CommunicationDto dto) {
		
		Communication comm = commMapper.fromDto(dto);
		UserDto appUser = this.userService.getCurrentUser();
    	String fullName = String.format("%s %s", appUser.getFirstName(), appUser.getLastName());
    	comm.setSenderName(fullName);
    	comm.setProgramSystemCode(programSystemCodeRepo.findById(appUser.getProgramSystemCode()).orElse(null));
    	
    	Communication communication = this.commRepo.save(comm);
    	if (dto.getAttachmentId() != null) {
	    	Attachment attachment = reportAttachmentsRepo.findById(dto.getAttachmentId()).orElse(null);
	    	attachment.setCommunication(communication);
	    	reportAttachmentsRepo.save(attachment);
    	}

		SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(comm.getProgramSystemCode().getCode());

		List<String> emailList = new ArrayList<>();
		List<UserFacilityAssociation> userList = this.userFacilityRepo.findByProgramSystemCode(comm.getProgramSystemCode().getCode());
		Map<Object,List<UserFacilityAssociationDto>> filteredUserList = userFacilityAssocService.mapAssociations(userList).stream()
				.filter(role -> role.getRoleDescription().equalsIgnoreCase(NEI_CERTIFIER_ROLE)
						|| role.getRoleDescription().equalsIgnoreCase(PREPARER_ROLE))
				.collect(Collectors.groupingBy(user -> user.getCdxUserId()));

		for (List<UserFacilityAssociationDto> ufa: filteredUserList.values()) {
			emailList.add(ufa.get(0).getEmail());
		}

		comm.setRecipientEmail(emailList.toString().substring(1, emailList.toString().length() - 1));
		commRepo.save(comm);
		notificationService.sendSLTNotification(sltConfig.getSltEmail(), cefConfig.getDefaultEmailAddress(), sltConfig.getSltEmail(), comm);
		
    	return comm;
	}
	
}
