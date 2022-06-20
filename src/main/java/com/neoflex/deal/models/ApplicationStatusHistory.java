package com.neoflex.deal.models;

import com.neoflex.deal.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "applicationStatusHistory")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private LocalDateTime time;

}