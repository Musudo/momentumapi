package com.musadzeyt.momentumapi.dataFaker;

import com.musadzeyt.momentumapi.dataFaker.generator.*;
import com.musadzeyt.momentumapi.repository.*;
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
    private final AppUserGenerator userGenerator;
    private final InstitutionRepository institutionRepository;
    private final ContactRepository contactRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final VoiceMemoRepository voiceMemoRepository;
    private final ReviewEmailRepository reviewEmailRepository;
    private final ReviewAttachmentRepository reviewAttachmentRepository;
    private final ActivityRepository activityRepository;
    private final ReviewRepository reviewRepository;
    private final AppUserRepository userRepository;

    public void seedData() {
        // !!! Order creations correctly to handle potential foreign key constraints

        tagGenerator.createTags();
        institutionGenerator.createInstitutions(5);
        contactGenerator.createContacts(50);
        activityGenerator.createActivitiesToday(3);
        activityGenerator.createActivitiesNextSevenDays(10);
        activityGenerator.createActivitiesNextThirtyDays(30);
        reviewGenerator.createReviews(60);
        reviewEmailGenerator.createEmails(60);
        reviewAttachmentGenerator.createAttachments(60);
        voiceMemoGenerator.createVoiceMemos(30);
    }

    public void eraseData() {
        // !!! Order deletions correctly to handle potential foreign key constraints

        voiceMemoRepository.deleteAll();
        reviewEmailRepository.deleteAll();
        reviewAttachmentRepository.deleteAll();
        reviewRepository.deleteAll();
        activityRepository.deleteAll();
        contactRepository.deleteAll();
        institutionRepository.deleteAll();
        tagRepository.deleteAll();
    }

    public void seedTestData() {
        // !!! Order creations correctly to handle potential foreign key constraints

        userGenerator.createTestUser();
        tagGenerator.createTags();
        institutionGenerator.createInstitutions(3);
        contactGenerator.createContacts(3);
        activityGenerator.createActivitiesToday(3);
        activityGenerator.createActivitiesNextSevenDays(3);
        activityGenerator.createActivitiesNextThirtyDays(3);
        reviewGenerator.createReviews(3);
        reviewEmailGenerator.createEmails(3);
        reviewAttachmentGenerator.createAttachments(3);
        voiceMemoGenerator.createVoiceMemos(3);
    }

    public void eraseTestData() {
        // !!! Order deletions correctly to handle potential foreign key constraints

        voiceMemoRepository.deleteAll();
        reviewEmailRepository.deleteAll();
        reviewAttachmentRepository.deleteAll();
        reviewRepository.deleteAll();
        activityRepository.deleteAll();
        contactRepository.deleteAll();
        institutionRepository.deleteAll();
        tagRepository.deleteAll();
        userRepository.deleteAll();
    }
}
