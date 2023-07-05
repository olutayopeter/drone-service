package com.blusalt.drone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@ToString
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final int expiresin;
    private final String code;
    private final String message;

}
