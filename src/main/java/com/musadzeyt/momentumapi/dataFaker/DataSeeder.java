package com.musadzeyt.momentumapi.dataFaker;

import com.musadzeyt.momentumapi.dataFaker.generator.*;
import com.musadzeyt.momentumapi.repository.*;
import com.musadzeyt.momentumapi.service.entityService.ActivityService;
import com.musadzeyt.momentumapi.service.entityService.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataSeeder {
    private final InstitutionGenerator institutionGenerator;
    private final ContactGenerator contactGenerator;
    private final ActivityGenerator activityGenerator;
    private final TagGenerator tagGenerator;
    private final ReviewGenerator reviewGenerator;
    private final VoiceMemoGenerator voiceMemoGenerator;
    private final ReviewEmailGenerator reviewEmailGenerator;
    private final ReviewAttachmentGenerator reviewAttachmentGenerator;
    private final IInstitutionRepository institutionRepository;
    private final IContactRepository contactRepository;
    private final ITagRepository tagRepository;
    private final ITaskRepository taskRepository;
    private final IVoiceMemoRepository voiceMemoRepository;
    private final IReviewEmailRepository reviewEmailRepository;
    private final IReviewAttachmentRepository reviewAttachmentRepository;
    private final ActivityService activityService;
    private final ReviewService reviewService;

    public void seedData() {
        // !!! Order creations correctly to handle potential foreign key constraints

        tagGenerator.createTags();
        institutionGenerator.createInstitutions(3);
        contactGenerator.createContacts(30);
        activityGenerator.createActivitiesToday(3);
        activityGenerator.createActivitiesNextSevenDays(5);
        activityGenerator.createActivitiesNextThirtyDays(10);
        reviewGenerator.createReviews(60);
        reviewEmailGenerator.createEmails(60);
        reviewAttachmentGenerator.createAttachments(60);
        voiceMemoGenerator.createVoiceMemos(30);
    }

    public void eraseData() {
        // !!! Order deletions correctly to handle potential foreign key constraints

        taskRepository.deleteAll();
        voiceMemoRepository.deleteAll();
        reviewEmailRepository.deleteAll();
        reviewAttachmentRepository.deleteAll();
        reviewService.deleteAll();
        activityService.deleteAll();
        contactRepository.deleteAll();
        institutionRepository.deleteAll();
        tagRepository.deleteAll();
    }
}
