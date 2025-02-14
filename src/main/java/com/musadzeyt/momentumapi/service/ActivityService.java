package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.dto.ActivityDto;
import com.musadzeyt.momentumapi.exception.ActivityNotFoundException;
import com.musadzeyt.momentumapi.repository.IActivityRepository;
import com.musadzeyt.momentumapi.util.mapper.IActivityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ActivityService {
    private final IActivityRepository activityRepository;
    private final IActivityMapper activityMapper;

    public List<ActivityDto> findAll() {
        List<Activity> activities = activityRepository.findAll();
        return activityMapper.entityListToDtoList(activities);
    }

    public ActivityDto findById(UUID id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        return activityMapper.entityToDto(activity);
    }

    public Activity create(ActivityDto activityDto) {
        Activity activity = activityMapper.dtoToEntity(activityDto);
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
