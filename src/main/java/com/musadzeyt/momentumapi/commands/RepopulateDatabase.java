package com.musadzeyt.momentumapi.commands;

import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * A {@link CommandLineRunner} implementation that empties and then repopulates the database with fake data.
 * </p>
 *
 * <p>
 * This class checks for the presence of the {@code --repopulate-db} command-line argument when
 * the Spring Boot application starts. If the argument is provided, it triggers database erasing and reseeding
 * process by invoking {@link DataSeeder#eraseData()} and {@link DataSeeder#seed()}.
 * </p>
 *
 * <p>
 * This feature is useful for development or testing environments where you need to quickly remove all data from your
 * database and then immediately seed it with sample data. Use with caution to avoid unintentional data manipulation
 * in production.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * # Using Gradle:
 * ./gradlew bootRun --args="--repopulate-db"
 *
 * # This command starts the Spring Boot application and, if the "--repopulate-db" flag is detected,
 * # executes the reseeding logic:
 * dataSeeder.eraseData();
 * dataSeeder.seed();
 * log.info("Database repopulated.");
 * </pre>
 *
 * <p>
 * Ensure that this operation is intended only for non-production environments.
 * </p>
 */
@Component
@Slf4j
@AllArgsConstructor
public class RepopulateDatabase implements CommandLineRunner {
    private final ApplicationArguments appArgs;
    private final DataSeeder dataSeeder;

    /**
     * Executes the database population logic if the {@code --repopulate-db} flag is present.
     *
     * @param args the command-line arguments passed to the application.
     * @throws Exception if an error occurs during data seeding.
     */
    @Override
    public void run(String... args) throws Exception {
        // Check if the --repopulate-db flag is present
        if (appArgs.containsOption("repopulate-db")) {
            dataSeeder.eraseData();
            dataSeeder.seed();
            log.info("Database repopulated.");
        }
    }
}
