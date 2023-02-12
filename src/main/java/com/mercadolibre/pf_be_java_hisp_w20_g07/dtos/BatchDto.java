package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.InboundOrder;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BatchDto {



    private Integer batchNumber;

    private int productId;

    private double currentTemperature;

    private double minimumTemperature;
    private int initialQuantity;
    private int currentQuantity;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate manufacturingDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    private Date manufacturingTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dueDate;


    //private Section section;
    //private InboundOrder inboundOrder;




}
