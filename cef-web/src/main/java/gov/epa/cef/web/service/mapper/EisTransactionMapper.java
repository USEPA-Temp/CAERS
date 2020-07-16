package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EisTransactionAttachment;
import gov.epa.cef.web.domain.EisTransactionHistory;
import gov.epa.cef.web.service.dto.EisTransactionAttachmentDto;
import gov.epa.cef.web.service.dto.EisTransactionHistoryDto;

@Mapper(componentModel = "spring")
public interface EisTransactionMapper {

    EisTransactionHistoryDto historyToDto(EisTransactionHistory source);

    List<EisTransactionHistoryDto> historyToDtoList(List<EisTransactionHistory> source);

    @Mapping(source="transactionHistoryId", target="transactionHistory.id")
    @Mapping(target="attachment", ignore = true)
    EisTransactionAttachment attachmentFromDto(EisTransactionAttachmentDto source);

    @Mapping(source="transactionHistory.id", target="transactionHistoryId")
    @Mapping(target="attachment", ignore = true)
    EisTransactionAttachmentDto attachmentToDto(EisTransactionAttachment source);

    List<EisTransactionAttachmentDto> attachmentToDtoList(List<EisTransactionAttachment> source);

}
