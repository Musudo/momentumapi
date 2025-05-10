package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.EmailDto;
import com.musadzeyt.momentumapi.dto.entityDto.ActivityDto;
import com.musadzeyt.momentumapi.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.service.CloudAmqpService;
import com.musadzeyt.momentumapi.service.entityService.ActivityService;
import com.musadzeyt.momentumapi.util.DateUtil;
import com.musadzeyt.momentumapi.util.mapper.IActivityMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final IActivityMapper activityMapper;
    private final CloudAmqpService cloudAmqpService;

    @GetMapping("")
    public ResponseEntity<List<ActivityDto>> findActivities() {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/today")
    public ResponseEntity<List<ActivityDto>> findActivitiesForToday() {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findAllForToday()), HttpStatus.OK);
    }

    @GetMapping("/next-seven-days")
    public ResponseEntity<List<ActivityDto>> findActivitiesForNextSevenDays() {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findAllForNextSevenDays()), HttpStatus.OK);
    }

    @GetMapping("/next-thirty-days")
    public ResponseEntity<List<ActivityDto>> findActivitiesForNextThirtyDays() {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findAllForNextThirtyDays()), HttpStatus.OK);
    }

    @GetMapping("/archived/{year}")
    public ResponseEntity<List<ActivityDto>> findArchivedActivities(@PathVariable String year) {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findAllArchived(year)), HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ActivityDto>> findActivitiesByType(@PathVariable String type) {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findByType(type)), HttpStatus.OK);
    }

    @GetMapping("/type/{type}/last-six-months")
    public ResponseEntity<List<ActivityDto>> findActivitiesByTypeForLastSixMonths(@PathVariable String type) {
        return new ResponseEntity<>(activityMapper.entityListToDtoList(activityService.findByTypeForIntervalOfDays(type, false, 180)), HttpStatus.OK);
    }

    @GetMapping("/type/{type}/last-six-months/amounts-per-month")
    public ResponseEntity<List<Map<String, Integer>>> findActivitiesAmountsByTypePerMonthForLastSixMonths(@PathVariable String type) {
        return new ResponseEntity<>(activityService.findAmountsByTypePerMonthForLastSixMonths(type), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> findActivity(@PathVariable UUID id) {
        return new ResponseEntity<>(activityMapper.entityToDto(activityService.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) {
        if (activityDto.getEmailSentAt() != null && !activityDto.getEmailSentAt().isBlank()) {
            if (activityDto.getContacts() != null && !activityDto.getContacts().isEmpty()) {
                activityDto.getContacts().forEach(dto -> {
                    EmailDto emailDto = new EmailDto(
                            dto.getEmail1(),
                            dto.getFirstName(),
                            "Activity confirmation",
                            activityDto.getSubject(),
                            DateUtil.formatDateIsoString(activityDto.getStartTime(), "full")
                    );
                    cloudAmqpService.sendMessageToEmailQueue(emailDto);
                });
            }

            if (activityDto.getExternalParticipants() != null && !activityDto.getExternalParticipants().isEmpty()) {
                activityDto.getExternalParticipants().forEach(dto -> {
                    EmailDto emailDto = new EmailDto(
                            dto.getEmail(),
                            dto.getName(),
                            "Activity confirmation",
                            activityDto.getSubject(),
                            DateUtil.formatDateIsoString(activityDto.getStartTime(), "full")
                    );
                    cloudAmqpService.sendMessageToEmailQueue(emailDto);
                });
            }
        }

        var activity = activityService.create(activityDto);
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{activityId}/add-participant", produces = "application/json")
    public ResponseEntity<ActivityDto> addParticipant(@PathVariable UUID activityId, @RequestBody Map<String, UUID> data) {
        var activity = activityService.addParticipant(activityId, data);
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{activityId}/add-external-participant", produces = "application/json")
    public ResponseEntity<ActivityDto> addExternalParticipant(@PathVariable UUID activityId, @RequestBody ExternalParticipantDto externalParticipantDto) {
        var activity = activityService.addExternalParticipant(activityId, externalParticipantDto);
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable UUID id, @NotNull @Valid @RequestBody ActivityDto activityDto) {
        var activity = activityService.update(id, activityDto);
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/external-note", produces = "application/json")
    public ResponseEntity<ActivityDto> updateExternalNote(@PathVariable UUID id, @NotNull @Valid @RequestBody Map<String, String> data) {
        var activity = activityService.updateExternalNote(id, data.get("externalNote"));
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/internal-note", produces = "application/json")
    public ResponseEntity<ActivityDto> updateInternalNote(@PathVariable UUID id, @NotNull @Valid @RequestBody Map<String, String> data) {
        var activity = activityService.updateInternalNote(id, data.get("internalNote"));
        return new ResponseEntity<>(activityMapper.entityToDto(activity), HttpStatus.OK);
    }

    @PostMapping(value = "/send-confirmation-email", produces = "application/json")
    public ResponseEntity<String> sendConfirmationEmail(@NotNull @Valid @RequestBody ActivityDto activityDto) {
        if (activityDto.getContacts() != null && !activityDto.getContacts().isEmpty()) {
            activityDto.getContacts().forEach(contactDto -> {
                EmailDto emailDto = new EmailDto(
                        contactDto.getEmail1(),
                        contactDto.getFirstName(),
                        "Activity confirmation",
                        activityDto.getSubject(),
                        DateUtil.formatDateIsoString(activityDto.getStartTime(), "full")
                );
                cloudAmqpService.sendMessageToEmailQueue(emailDto);

                activityService.updateEmailSentAt(activityDto.getId(), LocalDateTime.now());
            });
        }

        if (activityDto.getExternalParticipants() != null && !activityDto.getExternalParticipants().isEmpty()) {
            activityDto.getExternalParticipants().forEach(externalParticipantDto -> {
                EmailDto emailDto = new EmailDto(
                        externalParticipantDto.getEmail(),
                        externalParticipantDto.getName(),
                        "Activity confirmation",
                        activityDto.getSubject(),
                        DateUtil.formatDateIsoString(activityDto.getStartTime(), "full")
                );
                cloudAmqpService.sendMessageToEmailQueue(emailDto);

                activityService.updateEmailSentAt(activityDto.getId(), LocalDateTime.now());
            });
        }

        return new ResponseEntity<>("Email is being processed", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable UUID id) {
        activityService.delete(id);
        return new ResponseEntity<>("Activity deleted", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{activityId}/delete-participant/{contactId}")
    public ResponseEntity<String> deleteParticipant(@PathVariable UUID activityId, @PathVariable UUID contactId) {
        activityService.deleteParticipant(activityId, contactId);
        return new ResponseEntity<>("Participant deleted", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{activityId}/delete-external-participant/{externalParticipantId}")
    public ResponseEntity<String> deleteExternalParticipant(@PathVariable UUID activityId, @PathVariable UUID externalParticipantId) {
        activityService.deleteExternalParticipant(activityId, externalParticipantId);
        return new ResponseEntity<>("External participant deleted", HttpStatus.OK);
    }
}
