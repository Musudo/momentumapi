package com.musadzeyt.momentumapi.faker;

import com.musadzeyt.momentumapi.domain.*;
import com.musadzeyt.momentumapi.faker.generator.*;
import com.musadzeyt.momentumapi.repository.*;
import com.musadzeyt.momentumapi.service.ActivityService;
import com.musadzeyt.momentumapi.service.ReviewService;
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
    private final TaskGenerator taskGenerator;
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
        // Order creations to handle potential foreign key constraints

        User user = userGenerator.createUser();
        userRepository.save(user);
        User admin = userGenerator.createAdmin();
        userRepository.save(admin);

        List<Institution> institutions = institutionGenerator.createInstitutions(2);
        institutionRepository.saveAll(institutions);

        List<Tag> tags = tagGenerator.createTags();
        tagRepository.saveAll(tags);

        List<Activity> activitiesToday = activityGenerator.createActivitiesToday(1);
        activityRepository.saveAll(activitiesToday);
        List<Activity> activitiesNextSevenDays = activityGenerator.createActivitiesNextSevenDays(3);
        activityRepository.saveAll(activitiesNextSevenDays);
        List<Activity> activitiesNextThirtyDays = activityGenerator.createActivitiesNextThirtyDays(3);
        activityRepository.saveAll(activitiesNextThirtyDays);

        List<Contact> contacts = contactGenerator.createContacts(5);
        contactRepository.saveAll(contacts);

//        List<Task> tasks = taskGenerator.createTasks(4);
//        taskRepository.saveAll(tasks);

        List<Review> reviews = reviewGenerator.createReviews(10);
        reviewRepository.saveAll(reviews);

        List<ReviewEmail> reviewEmails = reviewEmailGenerator.createEmails(10);
        reviewEmailRepository.saveAll(reviewEmails);

        List<ReviewAttachment> reviewAttachments = reviewAttachmentGenerator.createAttachments(10);
        reviewAttachmentRepository.saveAll(reviewAttachments);

        List<VoiceMemo> voiceMemos = voiceMemoGenerator.createVoiceMemos(10);
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
