package uz.pdp.online.lesson_1_1.payload;

import lombok.Data;

@Data
public class WorkerDto {
    private String name;

    private Integer phoneNumber;

    private String street;

    private Integer homeNumber;

    private Integer departmentId;
}
