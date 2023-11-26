package org.dcg.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "StateChangeHistory")
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class StateChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long changeId;

    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private Application application;

    @Column(nullable = false)
    private String previousState;

    @Column(nullable = false)
    private String newState;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

    @Column
    private String reason;
}
