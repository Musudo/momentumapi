package com.musadzeyt.momentumapi.util.mapper;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.dto.ActivityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                IContactMapper.class,
                IInstitutionMapper.class,
                ITagMapper.class,
                IExternalParticipantMapper.class,
                IUserMapper.class
        }
)
public interface IActivityMapper {
    IActivityMapper INSTANCE = Mappers.getMapper(IActivityMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "institutionId", source = "institution.id")
    @Mapping(target = "institutionName", source = "institution.name")
    @Mapping(target = "tagIds", expression = "java(activity.getTags() != null ? activity.getTags().stream().map(t -> t.getId()).toList() : null)")
    @Mapping(target = "contactIds", expression = "java(activity.getContacts() != null ? activity.getContacts().stream().map(c -> c.getId()).toList() : null)")
    @Mapping(target = "externalParticipantIds", expression = "java(activity.getExternalParticipants() != null ? activity.getExternalParticipants().stream().map(p -> p.getId()).toList() : null)")
    ActivityDto entityToDto(Activity activity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "institution.id", source = "institutionId")
    Activity dtoToEntity(ActivityDto activityDto);

    List<ActivityDto> entityListToDtoList(List<Activity> list);

    List<Activity> dtoListToEntityList(List<ActivityDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "institution.id", source = "institutionId")
    Activity update(ActivityDto activityDto, @MappingTarget Activity activity);
}