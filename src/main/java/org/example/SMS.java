package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SMS {
    private boolean allowed;
    private String code;
    private String name;
    private String text;
    private String number;


}
