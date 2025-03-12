package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.*;
import com.musadzeyt.momentumapi.dto.ActivityDto;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.exception.ActivityNotFoundException;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.specification.ActivitySpecification;
import com.musadzeyt.momentumapi.util.mapper.IActivityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    private Specification<Activity> getUsernameSpec() {
        SearchCriteria criteria = new SearchCriteria("user.email", ":", getUsername());
        return new ActivitySpecification(criteria);
    }

    public List<Activity> findAll() {
        return activityRepository.findAll(getUsernameSpec());
    }

    public List<Map<String, Integer>> findAmountsPerDayForLastMonth() {
        return activityRepository.findAmountsPerDayForIntervalOfDays(29, getUsername());
    }

    public List<Activity> findByTypeForIntervalOfDays(String type, boolean interval, int days) {
        return activityRepository.findByTypeForIntervalOfDays(type, interval, days, getUsername());
    }

    public List<Activity> findByType(String type) {
        return findByTypeForIntervalOfDays(type, false, 365);
    }

    public List<Map<String, Integer>> findAmountsByTypePerMonthForLastSixMonths(String type) {
        return activityRepository.findAmountsByTypePerMonthForIntervalOfMonths(type, 6, getUsername());
    }

    public List<Activity> findAllForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDate = today.atStartOfDay(); // Start of today (00:00)
        LocalDateTime endDate = today.atTime(LocalTime.MAX); // End of today (23:59:59)

        SearchCriteria startTimeCriteria = new SearchCriteria("startTime", ">=", startDate);
        SearchCriteria endTimeCriteria = new SearchCriteria("startTime", "<=", endDate);

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(startTimeCriteria))
                .and(new ActivitySpecification(endTimeCriteria)));
    }

    public List<Activity> findAllForNextSevenDays() {
        LocalDate today = LocalDate.now().plusDays(1);
        LocalDateTime startDate = today.atStartOfDay(); // Start of today (00:00)
        LocalDateTime endDate = today.plusDays(7).atTime(LocalTime.MAX); // End of today (23:59:59)

        SearchCriteria startTimeCriteria = new SearchCriteria("startTime", ">=", startDate);
        SearchCriteria endTimeCriteria = new SearchCriteria("startTime", "<=", endDate);

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(startTimeCriteria))
                .and(new ActivitySpecification(endTimeCriteria)));
    }

    public List<Activity> findAllForNextThirtyDays() {
        LocalDate today = LocalDate.now().plusDays(8);
        LocalDateTime startDate = today.atStartOfDay(); // Start of today (00:00)
        LocalDateTime endDate = today.plusDays(30).atTime(LocalTime.MAX); // End of today (23:59:59)

        SearchCriteria startTimeCriteria = new SearchCriteria("startTime", ">=", startDate);
        SearchCriteria endTimeCriteria = new SearchCriteria("startTime", "<=", endDate);

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(startTimeCriteria))
                .and(new ActivitySpecification(endTimeCriteria)));
    }

    public Activity findById(UUID id) {
        return activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
    }

    public Activity create(ActivityDto activityDto) {
        Activity activity = activityMapper.dtoToEntity(activityDto);

        User user = userService.findById(activityDto.getUserId());
        activity.setUser(user);

        Institution institution = institutionService.findById(activityDto.getInstitutionId());
        activity.setInstitution(institution);

        Set<Tag> tags = new HashSet<>();
        activityDto.getTagIds().forEach(id -> {
            Tag tag = tagService.findById(id);
            tags.add(tag);
        });
        activity.setTags(tags);

        if (activityDto.getContactIds() != null) {
            Set<Contact> contacts = new HashSet<>();
            activityDto.getContactIds().forEach(id -> {
                Contact contact = contactService.findById(id);
                contacts.add(contact);
            });
            activity.setContacts(contacts);
        }

        if (activityDto.getExternalParticipantIds() != null) {
            Set<ExternalParticipant> externalParticipants = new HashSet<>();
            activityDto.getExternalParticipantIds().forEach(id -> {
                ExternalParticipant externalParticipant = externalParticipantService.findById(id);
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
