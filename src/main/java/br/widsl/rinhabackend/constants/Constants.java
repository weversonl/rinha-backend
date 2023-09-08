package br.widsl.rinhabackend.constants;

public class Constants {

    private Constants() {

    }

    public static final int ARRAY_ITEM_SIZE = 32;

    public static final int TTL_REDIS = 7200;

    public static final String ARRAY_ITEM_MESSAGE = "Invalid vector elements in stack field. Each item can be up to 32 characters";

    public static final String FIELD_REQUIRED = "%s is required";

    public static final String FIELD_SIZE = "%s must be {max} characters";

    public static final String INVALID_DATE = "Date of birth is required and must be in the pattern: YYYY-MM-DD";

    public static final String EXISTENT_PERSON = "Person with surname %s already exists!";

    public static final String SAVE_ERROR = "Error saving person with surname %s!";

    public static final String EMPTY_PERSON = "There is no person for ID %s!";

    public static final String BAD_REQUEST_EX = "Bad Request";

    public static final String BAD_REQUEST_DESC = "Unprocessable Entity/Content";

    public static final String INVALID_ID = "Invalid person ID";

    public static final String NOT_FOUND_EX = "Not Found";

    public static final String INTERNAL_SERVER_EX = "Internal Server Error";

    public static final String INTERNAL_SERVER_DESC = "There was an error processing data on the server";

}
