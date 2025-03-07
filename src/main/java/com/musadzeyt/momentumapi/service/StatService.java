package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.dto.stat.*;
import com.musadzeyt.momentumapi.util.StatUtil;
import com.musadzeyt.momentumapi.util.mapper.IInstitutionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StatService {
    private final ActivityService activityService;
    private final TaskService taskService;
    private final ReviewService reviewService;
    private final EmailService emailService;
    private final VoiceMemoService voiceMemoService;
    private final AttachmentService attachmentService;
    private final ContactService contactService;
    private final InstitutionService institutionService;

    public StatCardDto createActivitiesStatCardDto() {
        var data = activityService.findAmountsPerDayForLastMonth();

        List<String> datesList = StatUtil.extractStringValues(data, "date");
        List<Integer> statCardAmounts = StatUtil.extractIntValues(data, "amount");

        int value = statCardAmounts.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();
        var trend = StatUtil.trendCalculator(statCardAmounts);

        StatCardDto statCardDto = StatCardDto.builder()
                .title("Activities")
                .value(value)
                .caption("Last 30 days")
                .data(statCardAmounts)
                .trend(trend)
                .dates(datesList)
                .build();

        return statCardDto;
    }

    public StatCardDto createTasksStatCardDto() {
        var data = taskService.findAmountsPerDayForLastMonth();

        List<String> datesList = StatUtil.extractStringValues(data, "date");
        List<Integer> statCardAmounts = StatUtil.extractIntValues(data, "amount");

        int value = statCardAmounts.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();
        var trend = StatUtil.trendCalculator(statCardAmounts);

        StatCardDto statCardDto = StatCardDto.builder()
                .title("Tasks")
                .value(value)
                .data(statCardAmounts)
                .trend(trend)
                .dates(datesList)
                .build();

        return statCardDto;
    }

    public StatCardDto createReviewsStatCardDto() {
        var data = reviewService.findAmountsPerDayForLastMonth();

        List<String> datesList = StatUtil.extractStringValues(data, "date");
        List<Integer> statCardAmounts = StatUtil.extractIntValues(data, "amount");

        int value = statCardAmounts.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();
        var trend = StatUtil.trendCalculator(statCardAmounts);

        StatCardDto statCardDto = StatCardDto.builder()
                .title("Reviews")
                .value(value)
                .data(statCardAmounts)
                .trend(trend)
                .dates(datesList)
                .build();

        return statCardDto;
    }

    public BarChartDto createActivityTypesBarChartDto() {
        List<BarChartSeriesDto> barChartSeriesDtos = new ArrayList<>();

        var onlineActivities = activityService.findAmountsByTypePerMonthForLastSixMonths("online");
        // months list is normally the same for all activity types
        List<String> monthsList = StatUtil.extractStringValues(onlineActivities, "month");
        List<Integer> onlineActivityAmountsList = StatUtil.extractIntValues(onlineActivities, "amount");
        int valueOnlineActivities = onlineActivityAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        BarChartSeriesDto onlineActivitySeriesDto = BarChartSeriesDto.builder()
                .id("online")
                .label("Online")
                .data(onlineActivityAmountsList)
                .build();

        barChartSeriesDtos.add(onlineActivitySeriesDto);

        var phoneActivityAmounts = activityService.findAmountsByTypePerMonthForLastSixMonths("phone");
        List<Integer> phoneActivitiesAmountsList = StatUtil.extractIntValues(phoneActivityAmounts, "amount");
        int valuePhoneActivities = phoneActivitiesAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        BarChartSeriesDto phoneActivitySeriesDto = BarChartSeriesDto.builder()
                .id("phone")
                .label("Phone")
                .data(phoneActivitiesAmountsList)
                .build();

        barChartSeriesDtos.add(phoneActivitySeriesDto);

        var physicalActivitiesAmounts = activityService.findAmountsByTypePerMonthForLastSixMonths("physical");
        List<Integer> physicalActivityAmountsList = StatUtil.extractIntValues(physicalActivitiesAmounts, "amount");
        int valuePhysicalActivities = physicalActivityAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        BarChartSeriesDto physicalActivitySeriesDto = BarChartSeriesDto.builder()
                .id("physical")
                .label("Physical")
                .data(physicalActivityAmountsList)
                .build();

        barChartSeriesDtos.add(physicalActivitySeriesDto);

        var allAmounts = StatUtil.sumParallelValues(physicalActivityAmountsList, onlineActivityAmountsList, phoneActivitiesAmountsList);
        var trend = StatUtil.trendCalculator(allAmounts);

        BarChartDto barChartDto = BarChartDto.builder()
                .title("Activity types")
                .value(valuePhysicalActivities + valueOnlineActivities + valuePhoneActivities)
                .caption("Created activity types for the last 6 months")
                .trend(trend)
                .series(barChartSeriesDtos)
                .months(monthsList)
                .build();

        return barChartDto;
    }

    public LineChartDto createLineChartDto() {
        List<LineChartSeriesDto> lineChartSeriesDtos = new ArrayList<>();

        List<Map<String, Integer>> emails = emailService.findAmountsPerDayForLastMonth();
        // months list is normally the same for all activity types
        List<String> monthsList = StatUtil.extractStringValues(emails, "month");
        List<Integer> emailAmountsList = StatUtil.extractIntValues(emails, "amount");
        int valueEmails = emailAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        LineChartSeriesDto emailSeriesDto = LineChartSeriesDto.builder()
                .id("emails")
                .label("Emails")
                .data(emailAmountsList)
                .build();

        lineChartSeriesDtos.add(emailSeriesDto);

        List<Map<String, Integer>> voiceMemos = voiceMemoService.findAmountsPerDayForLastMonth();
        List<Integer> voiceMemoAmountsList = StatUtil.extractIntValues(voiceMemos, "amount");
        int valueVoiceMemos = voiceMemoAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        LineChartSeriesDto voiceMemoSeriesDto = LineChartSeriesDto.builder()
                .id("voiceMemos")
                .label("Voice Memos")
                .data(voiceMemoAmountsList)
                .build();

        lineChartSeriesDtos.add(voiceMemoSeriesDto);

        List<Map<String, Integer>> attachments = attachmentService.findAmountsPerDayForLastMonth();
        List<Integer> attachmentAmountsList = StatUtil.extractIntValues(attachments, "amount");
        int valueAttachments = attachmentAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        LineChartSeriesDto attachmentSeriesDto = LineChartSeriesDto.builder()
                .id("attachments")
                .label("Attachments")
                .data(attachmentAmountsList)
                .build();

        lineChartSeriesDtos.add(attachmentSeriesDto);

        LineChartDto lineChartDto = LineChartDto.builder()
                .title("Other data")
                .value(valueAttachments + valueEmails + valueVoiceMemos)
                .caption("Created emails, attachments and voice memo's per day for the last 30 days")
                .series(lineChartSeriesDtos)
                .months(monthsList)
                .build();

        return lineChartDto;
    }

//    public List<ContactTableColumnDto> createContactTableColumnDto() {
//        List<ContactTableColumnDto> columnDtos = new ArrayList<>();
//
//        ContactTableColumnDto firstNameColumn = ContactTableColumnDto.builder()
//                .field(StringUtil.toCamelCase(ColumnNameEnum.FIRST_NAME.getColumnName()))
//                .headerName(ColumnNameEnum.FIRST_NAME)
//                .minWidth(80)
//                .build();
//        columnDtos.add(firstNameColumn);
//
//        ContactTableColumnDto lastNameColumn = ContactTableColumnDto.builder()
//                .field(StringUtil.toCamelCase(ColumnNameEnum.LAST_NAME.getColumnName()))
//                .headerName(ColumnNameEnum.LAST_NAME)
//                .minWidth(80)
//                .build();
//        columnDtos.add(lastNameColumn);
//
//        ContactTableColumnDto emailColumnDto = ContactTableColumnDto.builder()
//                .field(StringUtil.toCamelCase(ColumnNameEnum.EMAIL.getColumnName()))
//                .headerName(ColumnNameEnum.EMAIL)
//                .minWidth(80)
//                .build();
//        columnDtos.add(emailColumnDto);
//
//        ContactTableColumnDto phoneColumnDto = ContactTableColumnDto.builder()
//                .field(StringUtil.toCamelCase(ColumnNameEnum.PHONE.getColumnName()))
//                .headerName(ColumnNameEnum.PHONE)
//                .minWidth(80)
//                .build();
//        columnDtos.add(phoneColumnDto);
//
//        ContactTableColumnDto jobTitleColumnDto = ContactTableColumnDto.builder()
//                .field(StringUtil.toCamelCase(ColumnNameEnum.JOB_TITLE.getColumnName()))
//                .headerName(ColumnNameEnum.JOB_TITLE)
//                .minWidth(80)
//                .build();
//        columnDtos.add(jobTitleColumnDto);
//
//        return columnDtos;
//    }

    public List<ContactTableDataDto> createContactTableDataDto() {
        List<ContactTableDataDto> contactTableDataDtos = new ArrayList<>();
        List<Contact> contacts = contactService.findAll();

        contacts.forEach(contact -> {
            ContactTableDataDto contactTableDataDto = ContactTableDataDto.builder()
                    .id(contact.getId())
                    .firstName(contact.getFirstName())
                    .lastName(contact.getLastName())
                    .email(contact.getEmail1())
                    .phone(contact.getPhone1())
                    .jobTitle(contact.getJobTitle())
                    .institutionName(IInstitutionMapper.INSTANCE.entityToDto(contact.getInstitution()).getName())
                    .build();
            contactTableDataDtos.add(contactTableDataDto);
        });

        return contactTableDataDtos;
    }
}
