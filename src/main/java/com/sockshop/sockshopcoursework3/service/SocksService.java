package com.sockshop.sockshopcoursework3.service;

import com.sockshop.sockshopcoursework3.model.ColorSocks;
import com.sockshop.sockshopcoursework3.model.SizeSocks;
import com.sockshop.sockshopcoursework3.model.Socks;

import javax.validation.constraints.Size;
import java.awt.*;

public interface SocksService {
    //Отпуск носков
    Socks editSocks(Socks socks, long quantity);

    Socks addSocks(Socks socks, long quantity);

    long getSocksNumByParam (ColorSocks color, SizeSocks size, int cottonMin, int cottonMax);


    boolean deleteSocks(Socks socks, long quantity);
}
