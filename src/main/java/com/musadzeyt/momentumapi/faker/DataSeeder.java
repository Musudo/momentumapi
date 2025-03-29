package com.musadzeyt.momentumapi.faker;

import com.musadzeyt.momentumapi.domain.*;
import com.musadzeyt.momentumapi.faker.generator.*;
import com.musadzeyt.momentumapi.repository.*;
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

    public void seed() {
        // Order creations to handle potential foreign key constraints

        User user = userGenerator.createUser();
        userRepository.save(user);
        User admin = userGenerator.createAdmin();
        userRepository.save(admin);

        List<Institution> institutions = institutionGenerator.createInstitutions(5);
        institutionRepository.saveAll(institutions);

        List<Tag> tags = tagGenerator.createTags();
        tagRepository.saveAll(tags);

        List<Activity> activitiesToday = activityGenerator.createActivitiesToday(3);
        activityRepository.saveAll(activitiesToday);
        List<Activity> activitiesNextSevenDays = activityGenerator.createActivitiesNextSevenDays(5);
        activityRepository.saveAll(activitiesNextSevenDays);
        List<Activity> activitiesNextThirtyDays = activityGenerator.createActivitiesNextThirtyDays(15);
        activityRepository.saveAll(activitiesNextThirtyDays);

        List<Contact> contacts = contactGenerator.createContacts(30);
        contactRepository.saveAll(contacts);

        List<Task> tasks = taskGenerator.createTasks(30);
        taskRepository.saveAll(tasks);

        List<Review> reviews = reviewGenerator.createReviews(30);
        reviewRepository.saveAll(reviews);

        List<ReviewEmail> reviewEmails = reviewEmailGenerator.createEmails(30);
        reviewEmailRepository.saveAll(reviewEmails);

        List<ReviewAttachment> reviewAttachments = reviewAttachmentGenerator.createAttachments(30);
        reviewAttachmentRepository.saveAll(reviewAttachments);

        List<VoiceMemo> voiceMemos = voiceMemoGenerator.createVoiceMemos(30);
        voiceMemoRepository.saveAll(voiceMemos);
    }

    public void eraseData() {
        // Order deletions to handle potential foreign key constraints
        voiceMemoRepository.deleteAll();
        reviewAttachmentRepository.deleteAll();
        reviewEmailRepository.deleteAll();
        reviewRepository.deleteAll();
        taskRepository.deleteAll();
        contactRepository.deleteAll();
        activityRepository.deleteAll();
        tagRepository.deleteAll();
        institutionRepository.deleteAll();
        userRepository.deleteAll();
    }
}
