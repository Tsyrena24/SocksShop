package com.sockshop.sockshopcoursework3.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sockshop.sockshopcoursework3.model.ColorSocks;
import com.sockshop.sockshopcoursework3.model.SizeSocks;
import com.sockshop.sockshopcoursework3.model.Socks;
import com.sockshop.sockshopcoursework3.service.FileSocksService;
import com.sockshop.sockshopcoursework3.service.SocksService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {

    private static Map<Socks, Long> socksMap = new HashMap<>();
    public final FileSocksService fileSocksService;

    public SocksServiceImpl(FileSocksService fileSocksService) {
        this.fileSocksService = fileSocksService;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Отпуск носков
    @Override
    public Socks editSocks(Socks socks, long quantity) {
        if (socksMap == null || socksMap.isEmpty()) {
            throw new UnsupportedOperationException("Socks map пуста");
        }
        if (quantity <= 0) {
            throw new UnsupportedOperationException("Количество должно быть положительным");
        }
        if (!socksMap.containsKey(socks)) {
            throw new UnsupportedOperationException("Socks не найдены");
        }
        long number = socksMap.get(socks) - quantity;
        if (number < 0) {
            throw new UnsupportedOperationException("Не достаточно носков на складе");
        }
        socksMap.put(socks, number);
        saveToFile();
        return socks;
    }

    @Override
    public Socks addSocks(Socks socks, long quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество носков должно быть больше нуля");
        }
        if (socks.getCottonPath() < 0 || socks.getCottonPath() > 100) {
            throw new IllegalArgumentException("Процент хлопка должен быть в диапазоне от 0 до 100");
        }
        socksMap.merge(socks, quantity, Long::sum);
        socksMap.putIfAbsent(socks, quantity);
        saveToFile();
        return socks;
    }


    @Override
    public long getSocksNumByParam(ColorSocks color, SizeSocks size, int cottonMin, int cottonMax) {
        if (cottonMin < 0 || cottonMax > 100 || cottonMax < cottonMin) {
            throw new IllegalArgumentException("Процентное соотношение хлопка должна быть между 0 и 100");
        }
        long count = socksMap.entrySet().stream()
                .filter(entry -> entry.getKey().getColorSocks().equals(color))
                .filter(entry -> entry.getKey().getSizeSocks().equals(size))
                .filter(entry -> entry.getKey().getCottonPath() >= cottonMin && entry.getKey().getCottonPath() <= cottonMax)
                .mapToLong(Map.Entry::getValue)
                .sum();
        return count;
    }

    @Override
    public boolean deleteSocks(Socks socks, long quantity) {
        if (quantity <= 0 || socksMap.get(socks) == null) {
            return false;
        }
        long number = socksMap.get(socks) - quantity;
        if (number < 0) {
            throw new IllegalArgumentException("Недостаточно носков на складе");
        }
        socksMap.put(socks, number);
        saveToFile();
        return true;
    }


    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileSocksService.readFile();
            socksMap = new ObjectMapper().readValue(json,
                    new TypeReference<HashMap<Socks, Long>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }






























}



