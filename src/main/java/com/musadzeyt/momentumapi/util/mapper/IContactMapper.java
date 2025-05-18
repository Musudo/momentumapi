package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.dto.entityDto.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAppUserMapper.class, IInstitutionMapper.class})
public interface IContactMapper {
    IContactMapper INSTANCE = Mappers.getMapper(IContactMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "institutionId", source = "institution.id")
    ContactDto entityToDto(Contact contact);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "institution.id", source = "institutionId")
    Contact dtoToEntity(ContactDto contactDto);

    List<ContactDto> entityListToDtoList(List<Contact> list);

    List<Contact> dtoListToEntityList(List<ContactDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "institution.id", source = "institutionId")
    Contact update(ContactDto contactDto, @MappingTarget Contact contact);
}
