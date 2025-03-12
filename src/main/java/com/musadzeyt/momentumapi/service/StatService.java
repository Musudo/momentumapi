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

    public StatCard createActivitiesStatCard() {
        var data = activityService.findAmountsPerDayForLastMonth();

        List<String> datesList = StatUtil.extractStringValues(data, "date");
        List<Integer> statCardAmounts = StatUtil.extractIntValues(data, "amount");

        int value = statCardAmounts.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();
        var trend = StatUtil.trendCalculator(statCardAmounts);

        StatCard statCard = StatCard.builder()
                .title("Activities")
                .value(value)
                .caption("Last 30 days")
                .data(statCardAmounts)
                .trend(trend)
                .dates(datesList)
                .build();

        return statCard;
    }

    public StatCard createTasksStatCard() {
        var data = taskService.findAmountsPerDayForLastMonth();

        List<String> datesList = StatUtil.extractStringValues(data, "date");
        List<Integer> statCardAmounts = StatUtil.extractIntValues(data, "amount");

        int value = statCardAmounts.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();
        var trend = StatUtil.trendCalculator(statCardAmounts);

        StatCard statCard = StatCard.builder()
                .title("Tasks")
                .value(value)
                .data(statCardAmounts)
                .trend(trend)
                .dates(datesList)
                .build();

        return statCard;
    }

    public StatCard createReviewsStatCard() {
        var data = reviewService.findAmountsPerDayForLastMonth();

        List<String> datesList = StatUtil.extractStringValues(data, "date");
        List<Integer> statCardAmounts = StatUtil.extractIntValues(data, "amount");

        int value = statCardAmounts.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();
        var trend = StatUtil.trendCalculator(statCardAmounts);

        StatCard statCard = StatCard.builder()
                .title("Reviews")
                .value(value)
                .data(statCardAmounts)
                .trend(trend)
                .dates(datesList)
                .build();

        return statCard;
    }

    public BarChart createActivityTypesBarChart() {
        List<BarChartSeries> barChartSeries = new ArrayList<>();

        var onlineActivities = activityService.findAmountsByTypePerMonthForLastSixMonths("online");
        // months list is normally the same for all activity types
        List<String> monthsList = StatUtil.extractStringValues(onlineActivities, "month");
        List<Integer> onlineActivityAmountsList = StatUtil.extractIntValues(onlineActivities, "amount");
        int valueOnlineActivities = onlineActivityAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        BarChartSeries onlineActivitySeries = BarChartSeries.builder()
                .id("online")
                .label("Online")
                .data(onlineActivityAmountsList)
                .build();

        barChartSeries.add(onlineActivitySeries);

        var phoneActivityAmounts = activityService.findAmountsByTypePerMonthForLastSixMonths("phone");
        List<Integer> phoneActivitiesAmountsList = StatUtil.extractIntValues(phoneActivityAmounts, "amount");
        int valuePhoneActivities = phoneActivitiesAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        BarChartSeries phoneActivitySeries = BarChartSeries.builder()
                .id("phone")
                .label("Phone")
                .data(phoneActivitiesAmountsList)
                .build();

        barChartSeries.add(phoneActivitySeries);

        var physicalActivitiesAmounts = activityService.findAmountsByTypePerMonthForLastSixMonths("physical");
        List<Integer> physicalActivityAmountsList = StatUtil.extractIntValues(physicalActivitiesAmounts, "amount");
        int valuePhysicalActivities = physicalActivityAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        BarChartSeries physicalActivitySeries = BarChartSeries.builder()
                .id("physical")
                .label("Physical")
                .data(physicalActivityAmountsList)
                .build();

        barChartSeries.add(physicalActivitySeries);

        var allAmounts = StatUtil.sumParallelValues(physicalActivityAmountsList, onlineActivityAmountsList, phoneActivitiesAmountsList);
        var trend = StatUtil.trendCalculator(allAmounts);

        BarChart barChart = BarChart.builder()
                .title("Activity types")
                .value(valuePhysicalActivities + valueOnlineActivities + valuePhoneActivities)
                .caption("Created activity types for the last 6 months")
                .trend(trend)
                .series(barChartSeries)
                .months(monthsList)
                .build();

        return barChart;
    }

    public LineChart createLineChart() {
        List<LineChartSeries> lineChartSeries = new ArrayList<>();

        List<Map<String, Integer>> emails = emailService.findAmountsPerDayForLastMonth();
        // months list is normally the same for all activity types
        List<String> monthsList = StatUtil.extractStringValues(emails, "month");
        List<Integer> emailAmountsList = StatUtil.extractIntValues(emails, "amount");
        int valueEmails = emailAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        LineChartSeries emailSeries = LineChartSeries.builder()
                .id("emails")
                .label("Emails")
                .data(emailAmountsList)
                .build();

        lineChartSeries.add(emailSeries);

        List<Map<String, Integer>> voiceMemos = voiceMemoService.findAmountsPerDayForLastMonth();
        List<Integer> voiceMemoAmountsList = StatUtil.extractIntValues(voiceMemos, "amount");
        int valueVoiceMemos = voiceMemoAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        LineChartSeries voiceMemoSeries = LineChartSeries.builder()
                .id("voiceMemos")
                .label("Voice Memos")
                .data(voiceMemoAmountsList)
                .build();

        lineChartSeries.add(voiceMemoSeries);

        List<Map<String, Integer>> attachments = attachmentService.findAmountsPerDayForLastMonth();
        List<Integer> attachmentAmountsList = StatUtil.extractIntValues(attachments, "amount");
        int valueAttachments = attachmentAmountsList.stream()
                .filter(num -> num != 0)
                .mapToInt(Integer::intValue)
                .sum();

        LineChartSeries attachmentSeries = LineChartSeries.builder()
                .id("attachments")
                .label("Attachments")
                .data(attachmentAmountsList)
                .build();

        lineChartSeries.add(attachmentSeries);

        LineChart lineChart = LineChart.builder()
                .title("Various data")
                .value(valueAttachments + valueEmails + valueVoiceMemos)
                .caption("Created emails, attachments and voice memo's per day for the last 30 days")
                .series(lineChartSeries)
                .months(monthsList)
                .build();

        return lineChart;
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

    public List<ContactTableData> createContactTableData() {
        List<ContactTableData> contactTableData = new ArrayList<>();
        List<Contact> contacts = contactService.findAll();

        contacts.forEach(contact -> {
            ContactTableData contactTableDataDto = ContactTableData.builder()
                    .id(contact.getId())
                    .firstName(contact.getFirstName())
                    .lastName(contact.getLastName())
                    .email(contact.getEmail1())
                    .phone(contact.getPhone1())
                    .jobTitle(contact.getJobTitle())
                    .institutionName(IInstitutionMapper.INSTANCE.entityToDto(contact.getInstitution()).getName())
                    .build();
            contactTableData.add(contactTableDataDto);
        });

        return contactTableData;
    }
}
