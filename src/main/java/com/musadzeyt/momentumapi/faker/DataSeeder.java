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
    private final EmailGenerator emailGenerator;
    private final AttachmentGenerator attachmentGenerator;
    private final IInstitutionRepository institutionRepository;
    private final IContactRepository contactRepository;
    private final IActivityRepository activityRepository;
    private final ITagRepository tagRepository;
    private final ITaskRepository taskRepository;
    private final IReviewRepository reviewRepository;
    private final IVoiceMemoRepository voiceMemoRepository;
    private final IEmailRepository emailRepository;
    private final IAttachmentRepository attachmentRepository;

    public void seed() {
        // Order creations to handle potential foreign key constraints

        List<Institution> institutions = institutionGenerator.createInstitutions(3);
        institutionRepository.saveAll(institutions);

        List<Tag> tags = tagGenerator.createTags();
        tagRepository.saveAll(tags);

        List<Activity> activities = activityGenerator.createActivities(20);
        activityRepository.saveAll(activities);

        List<Contact> contacts = contactGenerator.createContacts(20);
        contactRepository.saveAll(contacts);

        List<Task> tasks = taskGenerator.createTasks(10);
        taskRepository.saveAll(tasks);

        List<Review> reviews = reviewGenerator.createReviews(10);
        reviewRepository.saveAll(reviews);

        List<Email> emails = emailGenerator.createEmails(20);
        emailRepository.saveAll(emails);

        List<Attachment> attachments = attachmentGenerator.createAttachments(20);
        attachmentRepository.saveAll(attachments);

        List<VoiceMemo> voiceMemos = voiceMemoGenerator.createVoiceMemos(10);
        voiceMemoRepository.saveAll(voiceMemos);
    }

    public void eraseData() {
        // Order deletions to handle potential foreign key constraints
        voiceMemoRepository.deleteAll();
        attachmentRepository.deleteAll();
        emailRepository.deleteAll();
        reviewRepository.deleteAll();
        taskRepository.deleteAll();
        contactRepository.deleteAll();
        activityRepository.deleteAll();
        tagRepository.deleteAll();
        institutionRepository.deleteAll();
    }
}
