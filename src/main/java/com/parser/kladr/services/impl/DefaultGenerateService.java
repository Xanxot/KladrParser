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
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DefaultGenerateService implements GenerateService {
    @PersistenceContext
    EntityManager entityManager;

    private List<Value> valueList = new ArrayList<>();


    @Override
    @Transactional
    public void generate(GenerateRequest request) {
        List<Kladr> citys = entityManager.createQuery("SELECT k from Kladr k where k.socr in :values", Kladr.class)
                .setParameter("values", request.getSocrs()).getResultList();
        long of;
        int count = 0;
        if (request.getCountGenerate() == -1) {
            of = citys.size();
        } else {
            of = request.getCountGenerate();
        }

        for (int i = 0; i < of; i++) {
            var inspectedKladr = citys.get(i);
            var code = codeSelectGenerator(inspectedKladr.getCode());
            generateValue(inspectedKladr, code);
            StringBuilder builder = new StringBuilder();
            builder.append("Generate: ").append(i).append(" of: ").append(of).append(" / ").append(inspectedKladr.getName());
            System.out.println(builder);
            if (valueList.size() == 1000) {
                System.out.println("Buffer = "+ valueList.size() +" persist and cleaning buffer...");
                startToPersist();
                valueList = new ArrayList<>();
                System.gc();
                count += 1000;
            }
        }

        System.out.println("parse complete ... Count values: " + count + valueList.size());
        startToPersist();
        System.out.println("all done.");
    }

    @Transactional
    void startToPersist() {
        final int total = valueList.size();
        AtomicInteger i = new AtomicInteger();
        valueList.forEach(value ->
                {

                    System.out.println(i + " / " + total + " persist: " + value);
                    entityManager.persist(value);
                    i.getAndIncrement();

                }
        );
        entityManager.flush();
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

            valueList.add(value);

        }
        if (streets.size() == 0) {
            valueList.add(Value.builder()
                    .city(city)
                    .streets("-")
                    .build());
        }

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
