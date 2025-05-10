package com.musadzeyt.momentumapi.exception;

/**
 * Exception thrown when an entity cannot be found in the data store.
 * <p>
 * You can create this exception with a default message, a custom message,
 * by specifying the entity class, or by specifying both the entity class and identifier.
 * </p>
 * <p>Examples:</p>
 * <pre>{@code
 * throw new EntityNotFoundException();
 * throw new EntityNotFoundException("User not found");
 * throw new EntityNotFoundException(User.class);
 * throw new EntityNotFoundException(User.class, 42L);
 * }</pre>
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the default message "Entity not found".
     */
    public EntityNotFoundException() {
        super("Entity not found");
    }

    /**
     * Constructs a new exception with a custom message.
     *
     * @param message the detail message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception indicating a missing entity of the specified class.
     *
     * @param entityClass the class of the missing entity
     */
    public EntityNotFoundException(Class<?> entityClass) {
        super(entityClass.getSimpleName() + " not found");
    }

    /**
     * Constructs a new exception indicating a missing entity of the specified class
     * and identifier.
     *
     * @param entityClass the class of the missing entity
     * @param id          the identifier of the missing entity
     */
    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(entityClass.getSimpleName() + " with id " + id + " not found");
    }
}
