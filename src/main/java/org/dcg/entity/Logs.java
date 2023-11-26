package org.dcg.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Logs")
@Data
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String actionDescription;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

}
