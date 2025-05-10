package com.musadzeyt.momentumapi.commands;

import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * A {@link CommandLineRunner} implementation that populates the database with fake data.
 * </p>
 *
 * <p>
 * This class checks for the presence of the {@code --populate-db} command-line argument when
 * the Spring Boot application starts. If the argument is provided, it triggers the seeding
 * process by invoking {@link DataSeeder#seed()}.
 * </p>
 *
 * <p>
 * This is useful in development and testing environments where you need to clear the database.
 * Use with caution to avoid accidental data loss in production.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * # Using Gradle to run the application with the depopulation flag:
 * ./gradlew bootRun --args="--depopulate-db"
 *
 * # This command starts the Spring Boot application and, if the "--depopulate-db" flag is detected,
 * # executes the data erasing logic:
 * dataSeeder.eraseData();
 * log.info("Database depopulated.");
 *
 * # This will remove all records from the corresponding tables.
 * </pre>
 *
 * <p>
 * Ensure that the deletion order respects any foreign key constraints. In this example,
 * institutions are deleted first, followed by contacts.
 * </p>
 */
@Component
@Slf4j
@AllArgsConstructor
public class DepopulateDatabase implements CommandLineRunner {
    private final ApplicationArguments appArgs;
    private final DataSeeder dataSeeder;

    /**
     * Runs the database depopulation logic if the '--depopulate-db' flag is present.
     *
     * @param args command-line arguments passed to the application.
     * @throws Exception if an error occurs during database deletion.
     */
    @Override
    public void run(String... args) throws Exception {
        // Check if the --depopulate-db flag is present
        if (appArgs.containsOption("depopulate-db")) {
            dataSeeder.eraseData();
            log.info("Database depopulated.");
        }
    }
}
