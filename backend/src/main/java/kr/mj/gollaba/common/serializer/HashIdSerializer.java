package kr.mj.gollaba.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import kr.mj.gollaba.common.service.HashIdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class HashIdSerializer extends JsonSerializer<Long> {

    @Autowired(required=true)
    private HashIdService hashIdService;


    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(hashIdService.encode(value));
    }
}
