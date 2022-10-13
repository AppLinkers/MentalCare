package com.example.mentalCare.user.domain.Diagnose;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="test_id")
    private Long id;

    private String user_id;

    private String date;

    @OneToMany(mappedBy = "test")
    private List<Diagnose> diagnoseList;
}
