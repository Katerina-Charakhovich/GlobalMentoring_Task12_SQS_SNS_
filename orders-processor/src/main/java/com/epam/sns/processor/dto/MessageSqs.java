package com.epam.sns.processor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class MessageSqs {
    String Message;
}
