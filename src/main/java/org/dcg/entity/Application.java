package org.dcg.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Applications")
@Data
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(nullable = false)
    private String applicationName;

    @Column
    private String applicationContent;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @Column
    private String uniqueNumber;

    @OneToMany(mappedBy = "application")
    private Set<StateChangeHistory> stateChangeHistories;

}
