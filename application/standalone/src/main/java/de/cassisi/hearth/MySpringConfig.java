package de.cassisi.hearth;

import de.cassisi.hearth.port.CreateOperationJpaRepository;
import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.interactor.AddInfusionDataInteractor;
import de.cassisi.hearth.usecase.interactor.AddNirsDataInteractor;
import de.cassisi.hearth.usecase.interactor.CreateOperationInteractor;
import de.cassisi.hearth.usecase.port.AddInfusionDataRepository;
import de.cassisi.hearth.usecase.port.AddNirsDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfig {

    private final CreateOperationJpaRepository createOperationJpaRepository;
    private AddInfusionDataRepository addInfusionDataRepository;
    private AddNirsDataRepository addNirsDataRepository;

    public MySpringConfig(CreateOperationJpaRepository createOperationJpaRepository, AddInfusionDataRepository addInfusionDataRepository, AddNirsDataRepository addNirsDataRepository) {
        this.createOperationJpaRepository = createOperationJpaRepository;
        this.addInfusionDataRepository = addInfusionDataRepository;
        this.addNirsDataRepository = addNirsDataRepository;
    }


    @Bean
    public CreateOperation createOperation() {
        return new CreateOperationInteractor(createOperationJpaRepository);
    }

    @Bean
    public AddInfusionData addInfusionData() {
        return new AddInfusionDataInteractor(addInfusionDataRepository);
    }

    @Bean
    public AddNirsData addNirsData() {
        return new AddNirsDataInteractor(addNirsDataRepository);
    }
}
