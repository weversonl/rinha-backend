package br.widsl.rinhabackend.constants;

import java.util.regex.Pattern;

public class Constants {

    private Constants() {

    }

    public static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][\\d]|3[01])$");

    public static final int ARRAY_ITEM_SIZE = 32;

    public static final int TTL_REDIS = 1000;

    public static final String ARRAY_ITEM_MESSAGE = "Invalid vector elements in stack field. Each item can be up to 32 characters";

}
