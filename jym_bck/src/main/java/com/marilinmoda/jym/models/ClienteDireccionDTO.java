package com.marilinmoda.jym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ClienteDireccionDTO {
    private Integer dir_id;
    private String dir_sha;
    private String dir_calle;
}
