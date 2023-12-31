package org.dcg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApplicationDTO {
    private String applicationName;
    private String applicationContent;
}
