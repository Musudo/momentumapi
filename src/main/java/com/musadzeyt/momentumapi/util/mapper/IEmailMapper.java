package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Email;
import com.musadzeyt.momentumapi.dto.EmailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmailMapper {
    IEmailMapper INSTANCE = Mappers.getMapper(IEmailMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "reviewId", source = "review.id")
    EmailDto entityToDto(Email email);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Email dtoToEntity(EmailDto emailDto);

    List<EmailDto> entityListToDtoList(List<Email> list);

    List<Email> dtoListToEntityList(List<EmailDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Email update(EmailDto emailDto, @MappingTarget Email email);
}