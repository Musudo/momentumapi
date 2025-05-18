------------------------------
-- 1. Add foreign keys indexes
------------------------------

-- activity
CREATE INDEX idx_activity_user_id ON public.activity(user_id);
CREATE INDEX idx_activity_institution_id ON public.activity(institution_id);

-- activity_contact
CREATE INDEX idx_activity_contact_activity_id ON public.activity_contact(activity_id);
CREATE INDEX idx_activity_contact_contact_id ON public.activity_contact(contact_id);

-- activity_external_participant
CREATE INDEX idx_activity_external_participant_activity_id ON public.activity_external_participant(activity_id);
CREATE INDEX idx_activity_external_participant_external_participant_id ON public.activity_external_participant(external_participant_id);

-- activity_tag
CREATE INDEX idx_activity_tag_activity_id ON public.activity_tag(activity_id);
CREATE INDEX idx_activity_tag_tag_id ON public.activity_tag(tag_id);

-- contact
CREATE INDEX idx_contact_institution_id ON public.contact(institution_id);
CREATE INDEX idx_contact_user_id ON public.contact(user_id);

-- error_log
CREATE INDEX idx_error_log_user_id ON public.error_log(user_id);

-- review
CREATE INDEX idx_review_activity_id ON public.review(activity_id);
CREATE INDEX idx_review_user_id ON public.review(user_id);

-- review_attachment
CREATE INDEX idx_review_attachment_review_id ON public.review_attachment(review_id);

-- review_email
CREATE INDEX idx_review_email_review_id ON public.review_email(review_id);

-- review_review_attachment
CREATE INDEX idx_review_review_attachment_review_id ON public.review_review_attachment(review_id);
CREATE INDEX idx_review_review_attachment_attachment_id ON public.review_review_attachment(review_attachments_id);

-- review_review_email
CREATE INDEX idx_review_review_email_review_id ON public.review_review_email(review_id);
CREATE INDEX idx_review_review_email_email_id ON public.review_review_email(review_emails_id);

-- task
CREATE INDEX idx_task_user_id ON public.task(user_id);
CREATE INDEX idx_task_activity_id ON public.task(activity_id);

-- voice_memo
CREATE INDEX idx_voice_memo_user_id ON public.voice_memo(user_id);


-----------------------------------------
-- 2. Add frequently queried keys indexes
-----------------------------------------

-- app_user
CREATE UNIQUE INDEX idx_app_user_email ON public.app_user(email);

-- contact
CREATE UNIQUE INDEX idx_contact_email1 ON public.contact(email1);
CREATE INDEX idx_contact_email2 ON public.contact(email2);
CREATE UNIQUE INDEX idx_contact_phone1 ON public.contact(phone1);
CREATE INDEX idx_contact_phone2 ON public.contact(phone2);

-- tag
CREATE UNIQUE INDEX idx_tag_name ON public.tag(name);

-- institution
CREATE UNIQUE INDEX idx_institution_name ON public.institution(name);

-- review
CREATE INDEX idx_review_created_at ON public.review(created_at);

-- activity
CREATE INDEX idx_activity_start_time ON public.activity(start_time);
CREATE INDEX idx_activity_end_time ON public.activity(end_time);
