package com.mybank.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne(targetEntity = DepositType.class)
    @JoinColumn(name = "deposit_type_id",  columnDefinition = "bigint")
    private DepositType depositType;

    @Column
    private Integer countOfTriggers;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",  columnDefinition = "bigint")
    private User user;

    @Column
    private Double amount;
}
