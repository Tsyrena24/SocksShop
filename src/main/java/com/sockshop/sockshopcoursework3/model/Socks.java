package com.sockshop.sockshopcoursework3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    private ColorSocks colorSocks;
    private SizeSocks sizeSocks;
    private int cottonPath;

}
