package com.petstore.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.*;

@Data
@JsonIgnoreProperties
public class Order {

    private int id;
    private int petId;
    private int quantity;
    private OffsetDateTime shipDate;
    private String status;
    private boolean complete;


}
