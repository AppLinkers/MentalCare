package com.example.mentalCare.diagnose.domain;

import com.example.mentalCare.common.converter.BooleanToYNConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnose{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Convert(converter = BooleanToYNConverter.class)
    private Boolean deleted = Boolean.FALSE;
}
