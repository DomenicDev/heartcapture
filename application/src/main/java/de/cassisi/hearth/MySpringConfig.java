package de.cassisi.hearth;

import de.cassisi.hearth.port.CreateOperationJpaRepository;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.CreateOperationOld;
import de.cassisi.hearth.usecase.impl.CreateOperationInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfig {

    private final CreateOperationJpaRepository createOperationJpaRepository;

    public MySpringConfig(CreateOperationJpaRepository createOperationJpaRepository) {
        this.createOperationJpaRepository = createOperationJpaRepository;
    }


    @Bean
    public CreateOperation createOperation() {
        return new CreateOperationInteractor(createOperationJpaRepository);
    }

}
