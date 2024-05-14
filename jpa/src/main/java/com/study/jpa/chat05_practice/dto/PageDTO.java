package com.study.jpa.chat05_practice.dto;


import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PageDTO {

    private int page;
    private int size;

    //@noArg @@
    public PageDTO() {
        this.page = 1;
        this.size = 10;
    }


}
