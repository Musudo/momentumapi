-------------------
-- 1. Create tables
-------------------

-- Name: activity; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.activity (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    email_sent_at timestamp(6) without time zone,
    end_time timestamp(6) without time zone NOT NULL,
    external_note character varying(255),
    internal_note character varying(255),
    start_time timestamp(6) without time zone NOT NULL,
    subject character varying(100) NOT NULL,
    type character varying(25) NOT NULL,
    updated_at timestamp(6) without time zone,
    institution_id character varying(36),
    user_id character varying(36)
);
ALTER TABLE public.activity OWNER TO musa;

-- Name: activity_contact; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.activity_contact (
    activity_id character varying(36) NOT NULL,
    contact_id character varying(36) NOT NULL
);
ALTER TABLE public.activity_contact OWNER TO musa;

-- Name: activity_external_participant; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.activity_external_participant (
    activity_id character varying(36) NOT NULL,
    external_participant_id character varying(36) NOT NULL
);
ALTER TABLE public.activity_external_participant OWNER TO musa;

-- Name: activity_tag; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.activity_tag (
    activity_id character varying(36) NOT NULL,
    tag_id character varying(36) NOT NULL
);
ALTER TABLE public.activity_tag OWNER TO musa;

-- Name: app_user; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.app_user (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(100) NOT NULL,
    first_name character varying(25) NOT NULL,
    last_name character varying(25) NOT NULL,
    password character varying(255),
    roles text,
    updated_at timestamp(6) without time zone
);
ALTER TABLE public.app_user OWNER TO musa;

-- Name: contact; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.contact (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    email1 character varying(100) NOT NULL,
    email2 character varying(100),
    first_name character varying(25) NOT NULL,
    job_title character varying(50) NOT NULL,
    last_name character varying(25) NOT NULL,
    phone1 character varying(25) NOT NULL,
    phone2 character varying(25),
    updated_at timestamp(6) without time zone,
    institution_id character varying(36),
    user_id character varying(36)
);
ALTER TABLE public.contact OWNER TO musa;

-- Name: error_log; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.error_log (
    id character varying(36) NOT NULL,
    message character varying(255) NOT NULL,
    original_class character varying(255),
    original_line_number integer,
    original_method character varying(255),
    "timestamp" timestamp(6) without time zone,
    user_id character varying(36)
);
ALTER TABLE public.error_log OWNER TO musa;

-- Name: external_participant; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.external_participant (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(100) NOT NULL,
    name character varying(50) NOT NULL,
    updated_at timestamp(6) without time zone
);
ALTER TABLE public.external_participant OWNER TO musa;

-- Name: institution; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.institution (
    id character varying(36) NOT NULL,
    building_number character varying(10) NOT NULL,
    city character varying(100) NOT NULL,
    country_code character varying(10) NOT NULL,
    created_at timestamp(6) without time zone,
    name character varying(100) NOT NULL,
    postal_code character varying(10) NOT NULL,
    postbox character varying(10),
    street character varying(100) NOT NULL,
    updated_at timestamp(6) without time zone
);
ALTER TABLE public.institution OWNER TO musa;

-- Name: review; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.review (
    id character varying(36) NOT NULL,
    content text NOT NULL,
    created_at timestamp(6) without time zone,
    title character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    activity_id character varying(36),
    user_id character varying(36)
);
ALTER TABLE public.review OWNER TO musa;

-- Name: review_attachment; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.review_attachment (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    path character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    review_id character varying(36)
);
ALTER TABLE public.review_attachment OWNER TO musa;

-- Name: review_email; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.review_email (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(100) NOT NULL,
    updated_at timestamp(6) without time zone,
    review_id character varying(36)
);
ALTER TABLE public.review_email OWNER TO musa;

-- Name: review_review_attachment; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.review_review_attachment (
    review_id character varying(36) NOT NULL,
    review_attachments_id character varying(36) NOT NULL
);
ALTER TABLE public.review_review_attachment OWNER TO musa;

-- Name: review_review_email; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.review_review_email (
    review_id character varying(36) NOT NULL,
    review_emails_id character varying(36) NOT NULL
);
ALTER TABLE public.review_review_email OWNER TO musa;

-- Name: tag; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.tag (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    name character varying(100) NOT NULL,
    updated_at timestamp(6) without time zone
);
ALTER TABLE public.tag OWNER TO musa;

-- Name: task; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.task (
    id character varying(36) NOT NULL,
    completed boolean NOT NULL,
    created_at timestamp(6) without time zone,
    description character varying(100) NOT NULL,
    updated_at timestamp(6) without time zone,
    activity_id character varying(36),
    user_id character varying(36)
);
ALTER TABLE public.task OWNER TO musa;

-- Name: voice_memo; Type: TABLE; Schema: public; Owner: musa
CREATE TABLE public.voice_memo (
    id character varying(36) NOT NULL,
    created_at timestamp(6) without time zone,
    path character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    user_id character varying(36)
);
ALTER TABLE public.voice_memo OWNER TO musa;


---------------------
-- 2. Add constraints
---------------------

-- Name: activity activity_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_pkey PRIMARY KEY (id);

-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);

-- Name: contact contact_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (id);

-- Name: error_log error_log_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.error_log
    ADD CONSTRAINT error_log_pkey PRIMARY KEY (id);

-- Name: external_participant external_participant_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.external_participant
    ADD CONSTRAINT external_participant_pkey PRIMARY KEY (id);

-- Name: institution institution_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.institution
    ADD CONSTRAINT institution_pkey PRIMARY KEY (id);

-- Name: review_attachment review_attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_attachment
    ADD CONSTRAINT review_attachment_pkey PRIMARY KEY (id);

-- Name: review_email review_email_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_email
    ADD CONSTRAINT review_email_pkey PRIMARY KEY (id);

-- Name: review review_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_pkey PRIMARY KEY (id);

-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

-- Name: task task_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.task
    ADD CONSTRAINT task_pkey PRIMARY KEY (id);

-- Name: app_user uk1j9d9a06i600gd43uu3km82jw; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT uk1j9d9a06i600gd43uu3km82jw UNIQUE (email);

-- Name: tag uk1wdpsed5kna2y38hnbgrnhi5b; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.tag
    ADD CONSTRAINT uk1wdpsed5kna2y38hnbgrnhi5b UNIQUE (name);

-- Name: contact uk54gidfcvi6w6jt43dr4lkl3i5; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT uk54gidfcvi6w6jt43dr4lkl3i5 UNIQUE (email2);

-- Name: contact ukac5gtvxt1a868r0clx2miha4d; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT ukac5gtvxt1a868r0clx2miha4d UNIQUE (email1);

-- Name: review_review_email ukf6v8qpiissdx1kpbvdwvx27y2; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_review_email
    ADD CONSTRAINT ukf6v8qpiissdx1kpbvdwvx27y2 UNIQUE (review_emails_id);

-- Name: contact ukp8qviqiot59w1u8co2vom4wiq; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT ukp8qviqiot59w1u8co2vom4wiq UNIQUE (phone1);

-- Name: contact ukpjofo3nrux9y2gkbd9v0wgsax; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT ukpjofo3nrux9y2gkbd9v0wgsax UNIQUE (phone2);

-- Name: institution ukqhw15h5f7nc4g3ndva8sory1u; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.institution
    ADD CONSTRAINT ukqhw15h5f7nc4g3ndva8sory1u UNIQUE (name);

-- Name: review_review_attachment uktbnrg07125iy1r5hg11wobhsb; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_review_attachment
    ADD CONSTRAINT uktbnrg07125iy1r5hg11wobhsb UNIQUE (review_attachments_id);

-- Name: voice_memo voice_memo_pkey; Type: CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.voice_memo
    ADD CONSTRAINT voice_memo_pkey PRIMARY KEY (id);

-- Name: review_review_attachment fk1es0gtpoc4fb59naka5f4movm; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_review_attachment
    ADD CONSTRAINT fk1es0gtpoc4fb59naka5f4movm FOREIGN KEY (review_id) REFERENCES public.review(id);

-- Name: activity_contact fk27ait8m4gbg9aog2uvucfyhup; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity_contact
    ADD CONSTRAINT fk27ait8m4gbg9aog2uvucfyhup FOREIGN KEY (contact_id) REFERENCES public.contact(id);

-- Name: review_review_email fk2kwd8411r1m1ou3fqw1tvsk35; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_review_email
    ADD CONSTRAINT fk2kwd8411r1m1ou3fqw1tvsk35 FOREIGN KEY (review_id) REFERENCES public.review(id);

-- Name: review_attachment fk54xlvkn2od55vskx76dq2d1b4; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_attachment
    ADD CONSTRAINT fk54xlvkn2od55vskx76dq2d1b4 FOREIGN KEY (review_id) REFERENCES public.review(id);

-- Name: review fkc7y0l3wac4n2ewm6a2uecd54c; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review
    ADD CONSTRAINT fkc7y0l3wac4n2ewm6a2uecd54c FOREIGN KEY (user_id) REFERENCES public.app_user(id);

-- Name: activity_external_participant fkdduuuvvoae6y3xjfb8kfpruj1; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity_external_participant
    ADD CONSTRAINT fkdduuuvvoae6y3xjfb8kfpruj1 FOREIGN KEY (external_participant_id) REFERENCES public.external_participant(id);

-- Name: contact fke6j5lf4bavk5fmomrkhogp32v; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT fke6j5lf4bavk5fmomrkhogp32v FOREIGN KEY (institution_id) REFERENCES public.institution(id);

-- Name: review_review_attachment fkeaerc6e0nxlnpr2rtk4hlcgab; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_review_attachment
    ADD CONSTRAINT fkeaerc6e0nxlnpr2rtk4hlcgab FOREIGN KEY (review_attachments_id) REFERENCES public.review_attachment(id);

-- Name: activity_tag fkeg22rp70c734cacdgqfghojal; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity_tag
    ADD CONSTRAINT fkeg22rp70c734cacdgqfghojal FOREIGN KEY (activity_id) REFERENCES public.activity(id);

-- Name: activity_external_participant fkfjnc6hvxx73o5v3s1r98yuwtm; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity_external_participant
    ADD CONSTRAINT fkfjnc6hvxx73o5v3s1r98yuwtm FOREIGN KEY (activity_id) REFERENCES public.activity(id);

-- Name: activity fkfkc2v4iue4s9t8w8oqyr8s1vg; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity
    ADD CONSTRAINT fkfkc2v4iue4s9t8w8oqyr8s1vg FOREIGN KEY (institution_id) REFERENCES public.institution(id);

-- Name: review_review_email fkfm27hti5qyoe1536axd7buvox; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_review_email
    ADD CONSTRAINT fkfm27hti5qyoe1536axd7buvox FOREIGN KEY (review_emails_id) REFERENCES public.review_email(id);

-- Name: activity_tag fkg5abyuxlb6v2mjrtqvh1alls7; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity_tag
    ADD CONSTRAINT fkg5abyuxlb6v2mjrtqvh1alls7 FOREIGN KEY (tag_id) REFERENCES public.tag(id);

-- Name: voice_memo fkh6csh3s3g44m8nvottwx4387b; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.voice_memo
    ADD CONSTRAINT fkh6csh3s3g44m8nvottwx4387b FOREIGN KEY (user_id) REFERENCES public.app_user(id);

-- Name: activity_contact fkjb5donnqbkemb5pbqb1o29iw1; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity_contact
    ADD CONSTRAINT fkjb5donnqbkemb5pbqb1o29iw1 FOREIGN KEY (activity_id) REFERENCES public.activity(id);

-- Name: review fkk8c8ehger28p62uyajk5nm4xc; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review
    ADD CONSTRAINT fkk8c8ehger28p62uyajk5nm4xc FOREIGN KEY (activity_id) REFERENCES public.activity(id);

-- Name: contact fkmr27c898nvhtfwe5y05yucf3w; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.contact
    ADD CONSTRAINT fkmr27c898nvhtfwe5y05yucf3w FOREIGN KEY (user_id) REFERENCES public.app_user(id);

-- Name: error_log fknc8qhu5x5h3e30qf8x2e240vh; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.error_log
    ADD CONSTRAINT fknc8qhu5x5h3e30qf8x2e240vh FOREIGN KEY (user_id) REFERENCES public.app_user(id);

-- Name: task fksh6s1t7gbjr0ja7923du08fi8; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.task
    ADD CONSTRAINT fksh6s1t7gbjr0ja7923du08fi8 FOREIGN KEY (user_id) REFERENCES public.app_user(id);

-- Name: task fksu6jmc12at4mdrgalv496s7pc; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.task
    ADD CONSTRAINT fksu6jmc12at4mdrgalv496s7pc FOREIGN KEY (activity_id) REFERENCES public.activity(id);

-- Name: review_email fksykdju6ehpb8ylxn2mqt4a90d; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.review_email
    ADD CONSTRAINT fksykdju6ehpb8ylxn2mqt4a90d FOREIGN KEY (review_id) REFERENCES public.review(id);

-- Name: activity fkx6bphyf31hur0qb7k4afd5jo; Type: FK CONSTRAINT; Schema: public; Owner: musa
ALTER TABLE ONLY public.activity
    ADD CONSTRAINT fkx6bphyf31hur0qb7k4afd5jo FOREIGN KEY (user_id) REFERENCES public.app_user(id);
