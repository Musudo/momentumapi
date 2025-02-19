package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.dto.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IContactMapper {
    IContactMapper INSTANCE = Mappers.getMapper(IContactMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "institutionId", source = "institution.id")
    ContactDto entityToDto(Contact contact);

    @Mapping(target = "id", ignore = true) // This should be generated, so ignore
    @Mapping(target = "createdAt", ignore = true) // This should be generated, so ignore
    @Mapping(target = "updatedAt", ignore = true) // This should be generated, so ignore
    Contact dtoToEntity(ContactDto contactDto);

    List<ContactDto> entityListToDtoList(List<Contact> list);

    List<Contact> dtoListToEntityList(List<ContactDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Contact update(ContactDto contactDto, @MappingTarget Contact contact);
}