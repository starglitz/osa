package com.ftn.osa.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleQueryEs {

    private String field;
    private String value;

}
