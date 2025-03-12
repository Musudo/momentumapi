package com.musadzeyt.momentumapi.dto.stat;

import com.musadzeyt.momentumapi.enums.stat.TrendEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents a data transfer object for a statistical card.
 *
 * <p>This DTO encapsulates the following fields:</p>
 * <ul>
 *     <li><b>{@code title}</b> - The title or label for the statistical card.</li>
 *     <li><b>{@code value}</b> - The primary numerical value displayed on the card.</li>
 *     <li><b>{@code caption}</b> - A caption providing additional context or description for the statistic.</li>
 *     <li><b>{@code trend}</b> - A string representing the trend of the statistic (e.g., "up", "down", "neutral").</li>
 *     <li><b>{@code data}</b> - An array of additional data points or objects associated with the statistic.</li>
 *     <li><b>{@code year}</b> - The year for which the statistical data applies.</li>
 *     <li><b>{@code month}</b> - The month for which the statistical data applies.</li>
 * </ul>
 */
@Data
@Builder
public class StatCard {
    private String title;
    private int value;
    @Builder.Default
    private String caption = "Last 30 days";
    private TrendEnum trend;
    private List<Integer> data;
    private List<String> dates;
}
