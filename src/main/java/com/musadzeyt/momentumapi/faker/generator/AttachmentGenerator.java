package com.musadzeyt.momentumapi.faker.generator;

import com.musadzeyt.momentumapi.domain.Attachment;
import com.musadzeyt.momentumapi.faker.factory.AttachmentFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class AttachmentGenerator {
    private final AttachmentFactory attachmentFactory;

    /**
     * Creates a list of attachments using the AttachmentFactory.
     *
     * @param count the number of attachments to generate.
     * @return a List of generated Attachment objects.
     */
    public List<Attachment> createAttachments(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> attachmentFactory.create())
                .collect(Collectors.toList());
    }
}
