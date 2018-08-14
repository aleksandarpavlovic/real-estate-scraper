package com.paki.controller;

import com.paki.realties.enums.RealtyType;
import com.paki.scrape.topad.TopAdDefinition;
import com.paki.scrape.topad.TopAdDefinitions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topad")
public class TopAdController {

    @GetMapping(value = "definitions/{realtyType}")
    private List<TopAdDefinition> getDefinitions(@PathVariable("realtyType")RealtyType realtyType) {
        return TopAdDefinitions.getDefinitions(realtyType);
    }
}
