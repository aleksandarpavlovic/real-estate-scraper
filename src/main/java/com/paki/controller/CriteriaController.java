package com.paki.controller;

import com.paki.dto.criteria.definition.CriteriaDefinitionsDTO;
import com.paki.dto.transformers.CriteriaDTOTransformer;
import com.paki.realties.enums.RealtyType;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/criteria")
public class CriteriaController {

    private CriteriaDTOTransformer transformer;

    @Autowired
    public CriteriaController(CriteriaDTOTransformer transformer) {
        this.transformer = transformer;
    }

    @GetMapping(value = "/definitions/{realtyType}")
    private CriteriaDefinitionsDTO getDefinitions(@PathVariable("realtyType") RealtyType realtyType) {
        return transformer.transformCriteriaDefinitionsToDTO(CriteriaDefinitions.getDefinitions(realtyType));
    }
}
