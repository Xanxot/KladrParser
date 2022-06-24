package com.parser.kladr.services.impl;

import com.parser.kladr.model.Kladr;
import com.parser.kladr.model.Street;
import com.parser.kladr.model.Value;
import com.parser.kladr.services.GenerateService;
import com.parser.kladr.web.requests.GenerateRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DefaultGenerateService implements GenerateService {
    @PersistenceContext
    EntityManager entityManager;

    //  private final List<String> socrs = Arrays.asList("п", "г", "с", "д", "пгт");


    @Override
    @Transactional
    public void generate(GenerateRequest request) {
        List<Kladr> ass = entityManager.createQuery("SELECT k from Kladr k where k.socr in :values", Kladr.class)
                .setParameter("values", request.getSocrs()).getResultList();
        long of;
        if (request.getCountGenerate() == -1) {
            of = ass.size();
        } else {
            of = request.getCountGenerate();
        }

        for (int i = 0; i <= of; i++) {
            var inspectedKladr = ass.get(i);
            var code = codeSelectGenerator(inspectedKladr.getCode());
            generateValue(inspectedKladr, code);
            StringBuilder builder = new StringBuilder();
            builder.append("Generate: ").append(i).append(" of: ").append(of).append(" / ").append(inspectedKladr.getName());
            System.out.println(builder);
            entityManager.flush();

        }
    }

    @Transactional
    void generateValue(Kladr kladr, String code) {
        List<Street> streets = entityManager.createQuery("SELECT s from Street s where s.code like :value", Street.class)
                .setParameter("value", code).getResultList();
        String city = ";" + kladr.getSocr() + ". " + kladr.getName();

        for (int i = 0; i < streets.size(); i++) {
            var street = streets.get(i);
            var streetValue = street.getSocr() + ". " + street.getName();
            var value = Value.builder()
                    .city(city)
                    .streets(streetValue)
                    .build();

             flush(value);

        }

    }


    @Transactional
    void flush(Value value) {
        entityManager.persist(value);
    }

    private String codeSelectGenerator(String startCode) {
        char[] code = Arrays.copyOf(startCode.toCharArray(), 17);
        code[11] = '_';
        code[12] = '_';
        code[13] = '_';
        code[14] = '_';
        code[15] = '_';
        code[16] = '_';
        return new String(code);
    }
}
