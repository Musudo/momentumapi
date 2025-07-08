package com.musadzeyt.momentumapi.util;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.net.URI;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Utility class for working with iCalendar (.ics) objects, particularly for creating
 * activity or event calendar files in compliance with the iCalendar specification (RFC 5545).
 * <p>
 * Uses the iCal4j library to build and populate calendar objects, which can then be exported
 * or attached to emails for event invitation and tracking purposes.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * Calendar calendar = CalendarUtil.createActivityCalendar(
 *     "Team Meeting",
 *     "Monthly planning session.",
 *     LocalDateTime.of(2025, 7, 7, 9, 0),
 *     LocalDateTime.of(2025, 7, 7, 10, 0)
 * );
 * }</pre>
 * </p>
 *
 * @author Musa Tashtamirov
 * @since 1.0
 */
public class CalendarUtil {

    /**
     * Constructs an iCalendar object representing an activity or meeting.
     * <p>
     * The calendar will include a single {@link VEvent} with the given title, description,
     * start/end time, a random unique identifier, and a default organizer (noreply@momentumapp.com).
     * </p>
     *
     * @param title       the summary or subject of the event (displayed in calendar apps)
     * @param description the detailed description for the event
     * @param start       the local date and time at which the event starts
     * @param end         the local date and time at which the event ends
     * @return a populated {@link Calendar} object ready to be serialized or exported
     * @see net.fortuna.ical4j.model.Calendar
     * @see net.fortuna.ical4j.model.component.VEvent
     */
    public static Calendar createActivityCalendar(
            String title,
            String description,
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    ) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Momentum//iCal4j 2.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        String uid = UUID.randomUUID().toString();

        VEvent meeting = new VEvent(
                new DateTime(java.util.Date.from(start.atZone(ZoneId.systemDefault()).toInstant())),
                new DateTime(java.util.Date.from(end.atZone(ZoneId.systemDefault()).toInstant())),
                title
        );

        meeting.getProperties().add(new Uid(uid));
        meeting.getProperties().add(new Description(description));
        meeting.getProperties().add(new Organizer(URI.create("mailto:noreply@momentumapp.com")));

        calendar.getComponents().add(meeting);
        return calendar;
    }
}
