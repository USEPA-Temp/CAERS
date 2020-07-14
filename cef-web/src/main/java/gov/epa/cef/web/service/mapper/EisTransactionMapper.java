package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.EisTransactionHistory;
import gov.epa.cef.web.service.dto.EisTransactionHistoryDto;

@Mapper(componentModel = "spring")
public interface EisTransactionMapper {

    EisTransactionHistoryDto historyToDto(EisTransactionHistory source);

    List<EisTransactionHistoryDto> historyToDtoList(List<EisTransactionHistory> source);

}
