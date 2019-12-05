package alti.playground.explorecountry.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Country implements Serializable {
    private String id;
    private String iso2Code;
    private String name;
    private String capitalCity;
    private float longitude;
    private float latitude;
    private Adminregion adminregion;
    private IncomeLevel incomeLevel;
    private LendingType lendingType;
    private Region region;
}
