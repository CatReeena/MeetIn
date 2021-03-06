package com.shera.android.meetin;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Shera on 20.04.2018.
 */
public class JodaMoneyDeserializer extends StdDeserializer<Money> {
    public JodaMoneyDeserializer() {
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        String currency = node.findValue("currency").asText();
        BigDecimal amount = node.findValue("amount").decimalValue();
        return Money.of(CurrencyUnit.of(currency), amount);
    }
}
