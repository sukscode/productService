package dev.sukriti.productservice.InheritenceExample.SingleClass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sc_instructor")
@DiscriminatorValue("2")
public class Instructor extends User {
    private String batch;
}
