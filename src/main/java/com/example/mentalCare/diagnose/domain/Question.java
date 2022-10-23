package com.example.mentalCare.diagnose.domain;


import com.example.mentalCare.common.converter.BooleanToYNConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    private String keyword;

    private Integer weight;

    @Convert(converter = BooleanToYNConverter.class)
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "diagnose_id")
    private Diagnose diagnose;

}
