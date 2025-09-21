package dev.sukriti.productservice.Models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;
    private boolean isDeleted;
}
