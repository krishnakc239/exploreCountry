package alti.playground.explorecountry.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class IncomeLevel implements Serializable {
    private String id;
    private String iso2code;
    private String value;
}
