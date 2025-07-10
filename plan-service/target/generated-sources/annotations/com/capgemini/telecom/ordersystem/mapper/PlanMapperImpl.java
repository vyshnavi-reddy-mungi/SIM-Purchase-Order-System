package com.capgemini.telecom.ordersystem.mapper;

import com.capgemini.telecom.ordersystem.dto.PlanDTO;
import com.capgemini.telecom.ordersystem.model.Plan;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T15:48:34-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class PlanMapperImpl implements PlanMapper {

    @Override
    public PlanDTO modelToDto(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanDTO.PlanDTOBuilder planDTO = PlanDTO.builder();

        planDTO.id( plan.getId() );
        planDTO.planName( plan.getPlanName() );
        planDTO.dataPrice( plan.getDataPrice() );
        planDTO.internetPrice( plan.getInternetPrice() );
        planDTO.planType( plan.getPlanType() );
        planDTO.planPeriod( plan.getPlanPeriod() );
        planDTO.recurring( plan.isRecurring() );

        return planDTO.build();
    }

    @Override
    public List<PlanDTO> modelsToDtos(List<Plan> plan) {
        if ( plan == null ) {
            return null;
        }

        List<PlanDTO> list = new ArrayList<PlanDTO>( plan.size() );
        for ( Plan plan1 : plan ) {
            list.add( modelToDto( plan1 ) );
        }

        return list;
    }

    @Override
    public Plan dtoToModel(PlanDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }

        Plan.PlanBuilder plan = Plan.builder();

        plan.id( customerDTO.getId() );
        plan.planName( customerDTO.getPlanName() );
        plan.dataPrice( customerDTO.getDataPrice() );
        plan.internetPrice( customerDTO.getInternetPrice() );
        plan.planType( customerDTO.getPlanType() );
        plan.planPeriod( customerDTO.getPlanPeriod() );
        plan.recurring( customerDTO.isRecurring() );

        return plan.build();
    }
}
