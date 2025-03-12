package com.musadzeyt.momentumapi.commands;

import com.musadzeyt.momentumapi.faker.DataSeeder;
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
 * This feature is useful for development or testing environments where you need to seed your
 * database with sample data. Use with caution to avoid unintentional data manipulation in production.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * # Using Gradle:
 * ./gradlew bootRun --args="--populate-db"
 *
 * # This command starts the Spring Boot application and, if the "--populate-db" flag is detected,
 * # executes the seeding logic:
 * dataSeeder.seed();
 * log.info("Database populated.");
 * </pre>
 *
 * <p>
 * Ensure that this operation is intended only for non-production environments.
 * </p>
 */
@Component
@Slf4j
@AllArgsConstructor
public class PopulateDatabase implements CommandLineRunner {
    private final ApplicationArguments appArgs;
    private final DataSeeder dataSeeder;

    /**
     * Executes the database population logic if the {@code --populate-db} flag is present.
     *
     * @param args the command-line arguments passed to the application.
     * @throws Exception if an error occurs during data seeding.
     */
    @Override
    public void run(String... args) throws Exception {
        // Check if the --populate-db flag is present
        if (appArgs.containsOption("populate-db")) {
            dataSeeder.seed();
            log.info("Database populated.");
        }
    }
}
