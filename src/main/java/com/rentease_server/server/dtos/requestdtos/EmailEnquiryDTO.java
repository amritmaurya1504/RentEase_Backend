package com.rentease_server.server.dtos.requestdtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailEnquiryDTO {
    private String name;
    private String email;
    private String msg;
}
