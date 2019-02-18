package si.recek.wealthbuild.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

public class HalUtils {

    public static RestTemplate getRestTemplateWithHalMessageConverter() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> existingConverters = restTemplate.getMessageConverters();
        List<HttpMessageConverter<?>> newConverters = new ArrayList<>();
        newConverters.add(getHalMessageConverter());
        newConverters.addAll(existingConverters);
        restTemplate.setMessageConverters(newConverters);
        return restTemplate;
    }

    private static HttpMessageConverter getHalMessageConverter() {
        ObjectMapper objectMapper = getHalObjectMapper();
        MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        halConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON));
        halConverter.setObjectMapper(objectMapper);
        return halConverter;
    }

    public static ObjectMapper getHalObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        return objectMapper;
    }
}
