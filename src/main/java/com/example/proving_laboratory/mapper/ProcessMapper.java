package com.example.proving_laboratory.mapper;

import com.example.proving_laboratory.dto.AbstractProcessDto;
import com.example.proving_laboratory.dto.CleaningProcessDto;
import com.example.proving_laboratory.dto.DeliveryDto;
import com.example.proving_laboratory.dto.PaymentForWorkProcessDto;
import com.example.proving_laboratory.dto.ProductionProcessDto;
import com.example.proving_laboratory.entity.AbstractProcess;
import com.example.proving_laboratory.entity.CleaningProcess;
import com.example.proving_laboratory.entity.DeliveryReportProcess;
import com.example.proving_laboratory.entity.PaymentForWorkProcess;
import com.example.proving_laboratory.entity.ProductionProcess;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface ProcessMapper {

    CleaningProcess convertCleaningProcessDtoToCleaningProcess(CleaningProcessDto cleaningProcessDto);

    ProductionProcess convertProductionProcessDtoToProductionProcess(ProductionProcessDto productionProcessDto);

    DeliveryReportProcess convertDeliveryDtoToDeliveryReportProcess(DeliveryDto deliveryDto);

    PaymentForWorkProcess convertPaymentForWorkProcessDtoToPaymentForWorkProcess(PaymentForWorkProcessDto paymentForWorkProcessDto);

    default AbstractProcessDto convertAbstractProcessToAbstractProcessDto(AbstractProcess process) {
        AbstractProcessDto abstractProcessDto = new AbstractProcessDto(process.getProcessStart(), process.getProcessEnd(), process.getEmployee());
        if (process instanceof CleaningProcess) {
            abstractProcessDto.setCleaningEquipment(((CleaningProcess) process).getCleaningEquipment());
            abstractProcessDto.setProcedure(((CleaningProcess) process).getProcedure());
        } else if (process instanceof ProductionProcess) {
            abstractProcessDto.setProductName(((ProductionProcess) process).getProductName());
            abstractProcessDto.setSeriesNumber(((ProductionProcess) process).getSeriesNumber());
        } else if (process instanceof DeliveryReportProcess) {
            abstractProcessDto.setDeliveryReport(((DeliveryReportProcess) process).getDeliveryReport());
        } else {
            abstractProcessDto.setDeliveryReport(((PaymentForWorkProcess) process).getProofOfPayment());
        }
        return abstractProcessDto;
    }


}
