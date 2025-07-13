package beau.taapken.dogadoption.model;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Response {
    private ResponseCode responseCode;
    private String responseDescription;
}
