package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.*;
import com.musadzeyt.momentumapi.dto.ActivityDto;
import com.musadzeyt.momentumapi.dto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.exception.ActivityNotFoundException;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.repository.IExternalParticipantRepository;
import com.musadzeyt.momentumapi.specification.ActivitySpecification;
import com.musadzeyt.momentumapi.util.mapper.IActivityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityService {
    private final IExternalParticipantRepository iExternalParticipantRepository;
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
        LocalDateTime startTime = today.atStartOfDay(); // Start of today (00:00)
        LocalDateTime endTime = today.atTime(LocalTime.MAX); // End of today (23:59:59)

        SearchCriteria startTimeCriteria = new SearchCriteria("startTime", ">=", startTime);
        SearchCriteria endTimeCriteria = new SearchCriteria("startTime", "<=", endTime);

        Sort sort = Sort.by(Sort.Direction.ASC, "startTime");

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(startTimeCriteria))
                .and(new ActivitySpecification(endTimeCriteria)), sort);
    }

    public List<Activity> findAllForNextSevenDays() {
        LocalDate today = LocalDate.now().plusDays(1);
        LocalDateTime startTime = today.atStartOfDay(); // Start of today (00:00)
        LocalDateTime endTime = today.plusDays(7).atTime(LocalTime.MAX); // End of today (23:59:59)

        SearchCriteria startTimeCriteria = new SearchCriteria("startTime", ">=", startTime);
        SearchCriteria endTimeCriteria = new SearchCriteria("startTime", "<=", endTime);

        Sort sort = Sort.by(Sort.Direction.ASC, "startTime");

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(startTimeCriteria))
                .and(new ActivitySpecification(endTimeCriteria)), sort);
    }

    public List<Activity> findAllForNextThirtyDays() {
        LocalDate today = LocalDate.now().plusDays(8);
        LocalDateTime startTime = today.atStartOfDay(); // Start of today (00:00)
        LocalDateTime endTime = today.plusDays(30).atTime(LocalTime.MAX); // End of today (23:59:59)

        SearchCriteria startTimeCriteria = new SearchCriteria("startTime", ">=", startTime);
        SearchCriteria endTimeCriteria = new SearchCriteria("startTime", "<=", endTime);

        Sort sort = Sort.by(Sort.Direction.ASC, "startTime");

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(startTimeCriteria))
                .and(new ActivitySpecification(endTimeCriteria)), sort);
    }

    public List<Activity> findAllArchived(String yearStr) {
        LocalDateTime upperBound;
        LocalDateTime lowerBound;

        if (Integer.parseInt(yearStr) == LocalDate.now().getYear()) {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            upperBound = yesterday.atTime(LocalTime.MAX);
            lowerBound = LocalDate.of(Integer.parseInt(yearStr), 1, 1).atStartOfDay();
        } else {
            upperBound = LocalDate.of(Integer.parseInt(yearStr), 12, 31).atTime(LocalTime.MAX);
            lowerBound = LocalDate.of(Integer.parseInt(yearStr), 1, 1).atStartOfDay();
        }

        SearchCriteria upperBoundCriteria = new SearchCriteria("startTime", "<=", upperBound);
        SearchCriteria lowerBoundCriteria = new SearchCriteria("startTime", ">=", lowerBound);

        Sort sort = Sort.by(Sort.Direction.ASC, "startTime");

        return activityRepository.findAll(getUsernameSpec()
                .and(new ActivitySpecification(upperBoundCriteria))
                .and(new ActivitySpecification(lowerBoundCriteria)), sort);
    }

    public Activity findById(UUID id) {
        return activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
    }

    @Transactional
    public Activity create(ActivityDto activityDto) {
        if (activityDto.getEmailSentAt() == "") {
            activityDto.setEmailSentAt(null);
        }

        Activity activity = activityMapper.dtoToEntity(activityDto);

        User user = userService.findByEmail(getUsername());
        activity.setUser(user);

        if (activityDto.getInstitutionName() != "") {
            Institution institution = institutionService.findByName(activityDto.getInstitutionName());
            activity.setInstitution(institution);
        }

        List<Tag> tags = new ArrayList<>();
        activityDto.getTagIds().forEach(id -> {
            Tag tag = tagService.findById(id);
            tags.add(tag);
        });
        activity.setTags(tags);

        if (activityDto.getContactIds() != null && !activityDto.getContactIds().isEmpty()) {
            List<Contact> contacts = new ArrayList<>();
            activityDto.getContactIds().forEach(id -> {
                Contact contact = contactService.findById(id);
                contacts.add(contact);
            });
            activity.setContacts(contacts);
        }

        if (activityDto.getExternalParticipants() != null && !activityDto.getExternalParticipants().isEmpty()) {
            List<ExternalParticipant> externalParticipants = new ArrayList<>();
            activityDto.getExternalParticipants().forEach(externalParticipantDto -> {
                ExternalParticipant externalParticipant = externalParticipantService.create(externalParticipantDto);
                externalParticipants.add(externalParticipant);
            });
            activity.setExternalParticipants(externalParticipants);
        }

        return activityRepository.save(activity);
    }

    @Transactional
    public Activity addParticipant(UUID activityId, Map<String, UUID> data) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);
        Contact contact = contactService.findById(data.get("contactId"));
        activity.getContacts().add(contact);

        return activityRepository.save(activity);
    }

    @Transactional
    public Activity addExternalParticipant(UUID activityId, ExternalParticipantDto externalParticipantDto) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);
        ExternalParticipant externalParticipant = externalParticipantService.create(externalParticipantDto);
        activity.getExternalParticipants().add(externalParticipant);

        return activityRepository.save(activity);
    }

    @Transactional
    public Activity update(UUID id, ActivityDto activityDto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);

        activityMapper.update(activityDto, activity);

        List<Tag> currentTags = activity.getTags();
        Set<Tag> newTags = new HashSet<>(tagService.findAllById(activityDto.getTagIds()));

        currentTags.clear();
        currentTags.addAll(newTags);

        return activityRepository.save(activity);
    }

    @Transactional
    public Activity updateExternalNote(UUID id, String externalNote) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        activity.setExternalNote(externalNote);

        return activityRepository.save(activity);
    }

    @Transactional
    public Activity updateInternalNote(UUID id, String internalNote) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        activity.setInternalNote(internalNote);

        return activityRepository.save(activity);
    }

    @Transactional
    public void delete(UUID id) {
        Activity activity = activityRepository.findById(id).orElseThrow(ActivityNotFoundException::new);
        activity.getContacts().clear();
        activity.getExternalParticipants().clear();
        activity.getTags().clear();
        activity.setInstitution(null);
        activity.setUser(null);

        activityRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        List<Activity> activities = activityRepository.findAll();
        for (Activity activity : activities) {
            delete(activity.getId());
        }
    }

    @Transactional
    public void deleteParticipant(UUID activityId, UUID contactId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);
        activity.getContacts().removeIf(contact -> contact.getId().equals(contactId));
    }

    @Transactional
    public void deleteExternalParticipant(UUID activityId, UUID externalParticipantId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);

        // Find the specific contact from the activity's collection
        ExternalParticipant externalParticipant = activity.getExternalParticipants()
                .stream()
                .filter(ep -> ep.getId().equals(externalParticipantId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("External participant not found"));

        // Remove the association from the activity's collection
        activity.getExternalParticipants().remove(externalParticipant);

        // Delete the child record from the database
        iExternalParticipantRepository.delete(externalParticipant);
    }
}
