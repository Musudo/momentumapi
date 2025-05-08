package com.musadzeyt.momentumapi.dataFaker;

import com.musadzeyt.momentumapi.domain.*;
import com.musadzeyt.momentumapi.dataFaker.generator.*;
import com.musadzeyt.momentumapi.repository.*;
import com.musadzeyt.momentumapi.service.entityService.ActivityService;
import com.musadzeyt.momentumapi.service.entityService.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final UserGenerator userGenerator;
    private final IInstitutionRepository institutionRepository;
    private final IContactRepository contactRepository;
    private final IActivityRepository activityRepository;
    private final ITagRepository tagRepository;
    private final ITaskRepository taskRepository;
    private final IReviewRepository reviewRepository;
    private final IVoiceMemoRepository voiceMemoRepository;
    private final IReviewEmailRepository reviewEmailRepository;
    private final IReviewAttachmentRepository reviewAttachmentRepository;
    private final IUserRepository userRepository;

    private final ActivityService activityService;
    private final ReviewService reviewService;

    public void seed() {
        // Order creations correctly to handle potential foreign key constraints

        User user = userGenerator.createUser();
        userRepository.save(user);

        List<Institution> institutions = institutionGenerator.createInstitutions(3);
        institutionRepository.saveAll(institutions);

        List<Tag> tags = tagGenerator.createTags();
        tagRepository.saveAll(tags);

        List<Activity> activitiesToday = activityGenerator.createActivitiesToday(3);
        activityRepository.saveAll(activitiesToday);
        List<Activity> activitiesNextSevenDays = activityGenerator.createActivitiesNextSevenDays(5);
        activityRepository.saveAll(activitiesNextSevenDays);
        List<Activity> activitiesNextThirtyDays = activityGenerator.createActivitiesNextThirtyDays(10);
        activityRepository.saveAll(activitiesNextThirtyDays);

        List<Contact> contacts = contactGenerator.createContacts(30);
        contactRepository.saveAll(contacts);

        List<Review> reviews = reviewGenerator.createReviews(60);
        reviewRepository.saveAll(reviews);

        List<ReviewEmail> reviewEmails = reviewEmailGenerator.createEmails(60);
        reviewEmailRepository.saveAll(reviewEmails);

        List<ReviewAttachment> reviewAttachments = reviewAttachmentGenerator.createAttachments(60);
        reviewAttachmentRepository.saveAll(reviewAttachments);

        List<VoiceMemo> voiceMemos = voiceMemoGenerator.createVoiceMemos(30);
        voiceMemoRepository.saveAll(voiceMemos);
    }

    public void eraseData() {
        // Order deletions correctly to handle potential foreign key constraints

        taskRepository.deleteAll();
        voiceMemoRepository.deleteAll();
        reviewEmailRepository.deleteAll();
        reviewAttachmentRepository.deleteAll();
        reviewService.deleteAll();
        activityService.deleteAll();
        tagRepository.deleteAll();
        contactRepository.deleteAll();
        institutionRepository.deleteAll();
        userRepository.deleteAll();
    }
}
