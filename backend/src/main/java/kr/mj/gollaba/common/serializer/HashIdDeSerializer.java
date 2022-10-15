package kr.mj.gollaba.common.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import kr.mj.gollaba.common.service.HashIdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class HashIdDeSerializer extends JsonDeserializer<Long> {

    @Autowired(required=true)
    private HashIdService hashIdService;

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return hashIdService.decode(p.getText());
    }
}
