package com.musadzeyt.momentumapi.dataFaker.generator;

import com.musadzeyt.momentumapi.domain.ReviewAttachment;
import com.musadzeyt.momentumapi.dataFaker.factory.ReviewAttachmentFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class ReviewAttachmentGenerator {
    private final ReviewAttachmentFactory reviewAttachmentFactory;

    /**
     * Creates a list of attachments using the AttachmentFactory.
     *
     * @param count the number of attachments to generate.
     * @return a List of generated Attachment objects.
     */
    public List<ReviewAttachment> createAttachments(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> reviewAttachmentFactory.create())
                .collect(Collectors.toList());
    }
}
