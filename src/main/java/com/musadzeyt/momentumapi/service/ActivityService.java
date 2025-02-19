package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.*;
import com.musadzeyt.momentumapi.dto.ActivityDto;
import com.musadzeyt.momentumapi.exception.ActivityNotFoundException;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.util.mapper.IActivityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ActivityService {
    private final IActivityRepository activityRepository;
    private final IActivityMapper activityMapper;
    private final TagService tagService;
    private final InstitutionService institutionService;
    private final UserService userService;
    private final ContactService contactService;
    private final ExternalParticipantService externalParticipantService;

    public List<ActivityDto> findAll() {
        List<Activity> activities = activityRepository.findAll();
        return activityMapper.entityListToDtoList(activities);
    }

    public ActivityDto findActivityDtoById(UUID id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        return activityMapper.entityToDto(activity);
    }

    public Activity findActivityById(UUID id) {
        return activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
    }

    public Activity create(ActivityDto activityDto) {
        Activity activity = activityMapper.dtoToEntity(activityDto);

        User user = userService.findUserById(activityDto.getUserId());
        activity.setUser(user);

        Institution institution = institutionService.findInstitutionById(activityDto.getInstitutionId());
        activity.setInstitution(institution);

        Set<Tag> tags = new HashSet<>();
        activityDto.getTagIds().forEach(id -> {
            Tag tag = tagService.findTagById(id);
            tags.add(tag);
        });
        activity.setTags(tags);

        if (activityDto.getContactIds() != null) {
            Set<Contact> contacts = new HashSet<>();
            activityDto.getContactIds().forEach(id -> {
                Contact contact = contactService.findContactById(id);
                contacts.add(contact);
            });
            activity.setContacts(contacts);
        }

        if (activityDto.getExternalParticipantIds() != null) {
            Set<ExternalParticipant> externalParticipants = new HashSet<>();
            activityDto.getExternalParticipantIds().forEach(id -> {
                ExternalParticipant externalParticipant = externalParticipantService.findExternalParticipantById(id);
                externalParticipants.add(externalParticipant);
            });
            activity.setExternalParticipants(externalParticipants);
        }

        return activityRepository.save(activity);
    }

    public Activity update(UUID id, ActivityDto activityDto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        activityMapper.update(activityDto, activity);
        return activityRepository.save(activity);
    }

    public void delete(UUID id) {
        activityRepository.deleteById(id);
    }
}
