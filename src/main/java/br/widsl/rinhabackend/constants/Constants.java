package br.widsl.rinhabackend.constants;

import java.util.regex.Pattern;

public class Constants {

    private Constants() {

    }

    public static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][\\d]|3[01])$");

    public static final Pattern NAME_VERIFY_PATTERN = Pattern.compile("^[\\p{L}\\s]+$");

    public static final int ARRAY_ITEM_SIZE = 32;

    public static final int TTL_REDIS = 120;

    public static final int BATCH_TIME = 2000;

    public static final String ARRAY_ITEM_MESSAGE = "Invalid vector elements in stack field. Each item can be up to 32 characters";

    public static final String FIELD_REQUIRED = "%s is required";

    public static final String FIELD_SIZE = "%s must be {max} characters";

    public static final String INVALID_DATE = "Date of birth is invalid. Must be in the pattern: YYYY-MM-DD";

    public static final String NAME_ERROR_VALIDATION = "The name must only contain letters";

    public static final String EXISTENT_PERSON = "Person with surname %s already exists!";

    public static final String SAVE_ERROR = "Error saving person with surname %s!";

    public static final String EMPTY_PERSON = "There is no person for ID %s!";

    public static final String BAD_REQUEST_EX = "Bad Request";

    public static final String BAD_REQUEST_DESC = "There are validation errors in the fields sent";

    public static final String INVALID_ID = "Invalid person ID";

    public static final String NOT_FOUND_EX = "Not Found";

    public static final String DATABASE_EX = "Unprocessable Entity/Content";

    public static final String DATABASE_EX_DESC = "There was an error processing data on database";

}
