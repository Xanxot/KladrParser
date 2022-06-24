package com.parser.kladr.web.requests;

import lombok.Data;

import java.util.List;

@Data
public class GenerateRequest {
    private Long countGenerate;
    private List<String> socrs;
}
