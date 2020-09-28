package de.cassisi.hearth;

import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.interactor.AddInfusionDataInteractor;
import de.cassisi.hearth.usecase.interactor.CreateOperationInteractor;
import de.cassisi.hearth.usecase.port.AddInfusionDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfig {

    private final CreateOperationJpaRepository createOperationJpaRepository;
    private AddInfusionDataRepository addInfusionDataRepository;

    public MySpringConfig(CreateOperationJpaRepository createOperationJpaRepository, AddInfusionDataRepository addInfusionDataRepository) {
        this.createOperationJpaRepository = createOperationJpaRepository;
        this.addInfusionDataRepository = addInfusionDataRepository;
    }


    @Bean
    public CreateOperation createOperation() {
        return new CreateOperationInteractor(createOperationJpaRepository);
    }

    @Bean
    public AddInfusionData addInfusionData() {
        return new AddInfusionDataInteractor(addInfusionDataRepository);
    }
}
