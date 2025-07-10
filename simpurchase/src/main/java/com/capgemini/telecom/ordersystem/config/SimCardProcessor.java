package com.capgemini.telecom.ordersystem.config;

import com.capgemini.telecom.ordersystem.model.SimCard;
import org.springframework.batch.item.ItemProcessor;

public class SimCardProcessor  implements ItemProcessor<SimCard, SimCard> {

    @Override
    public SimCard process(SimCard simCard) throws Exception {


        return simCard;
    }
}
