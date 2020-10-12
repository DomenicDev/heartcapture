package de.cassisi.hearth;

import de.cassisi.hearth.port.CreateOperationJpaRepository;
import de.cassisi.hearth.port.HLMExcelFileReader;
import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.interactor.AddInfusionDataInteractor;
import de.cassisi.hearth.usecase.interactor.AddNirsDataInteractor;
import de.cassisi.hearth.usecase.interactor.CreateOperationInteractor;
import de.cassisi.hearth.usecase.interactor.ReadHLMDataFileInteractor;
import de.cassisi.hearth.usecase.port.AddInfusionDataRepository;
import de.cassisi.hearth.usecase.port.AddNirsDataRepository;
import de.cassisi.hearth.usecase.port.HLMFileReader;
import de.cassisi.hearth.usecase.port.ReadHLMDataFileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfig {

    private final CreateOperationJpaRepository createOperationJpaRepository;
    private final AddInfusionDataRepository addInfusionDataRepository;
    private final AddNirsDataRepository addNirsDataRepository;
    private final ReadHLMDataFileRepository readHLMDataFileRepository;

    private final HLMFileReader hlmFileReader = new HLMExcelFileReader();

    public MySpringConfig(CreateOperationJpaRepository createOperationJpaRepository, AddInfusionDataRepository addInfusionDataRepository, AddNirsDataRepository addNirsDataRepository, ReadHLMDataFileRepository readHLMDataFileRepository) {
        this.createOperationJpaRepository = createOperationJpaRepository;
        this.addInfusionDataRepository = addInfusionDataRepository;
        this.addNirsDataRepository = addNirsDataRepository;
        this.readHLMDataFileRepository = readHLMDataFileRepository;
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

    @Bean
    public ReadHLMDataFile readHLMDataFile() {
        return new ReadHLMDataFileInteractor(readHLMDataFileRepository, hlmFileReader);
    }

    @Bean
    public GenerateReport generateReport() {
        return null;
    }
}
