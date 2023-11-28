package org.dcg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ApplicationDTO {

    private Long applicationId;
    private String applicationName;
    private String state;
    private Date creationDate;
    private String applicationContent;
}
