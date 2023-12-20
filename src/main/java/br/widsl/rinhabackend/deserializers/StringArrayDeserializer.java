package br.widsl.rinhabackend.deserializers;

import br.widsl.rinhabackend.exception.impl.BadRequestException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class StringArrayDeserializer extends JsonDeserializer<String[]> {

    @Override
    public String[] deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isArray()) {
            int size = node.size();
            String[] result = new String[size];
            for (int i = 0; i < size; i++) {
                JsonNode element = node.get(i);
                if (!element.isTextual()) {
                    throw new BadRequestException("Array contains non-string element at index " + i);
                }
                result[i] = element.asText();
            }
            return result;
        } else {
            throw new BadRequestException("Expected an array of strings, but found: " + node);
        }
    }
}
