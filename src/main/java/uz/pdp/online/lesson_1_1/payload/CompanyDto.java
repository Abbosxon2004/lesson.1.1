package uz.pdp.online.lesson_1_1.payload;

import lombok.Data;

@Data
public class CompanyDto {
    private String corpName;

    private String directorName;

    private String street;

    private Integer homeNumber;
}
