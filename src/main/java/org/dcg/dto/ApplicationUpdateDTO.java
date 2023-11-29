package org.dcg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApplicationUpdateDTO {
    private Long applicationId;
    private String applicationContent;
}