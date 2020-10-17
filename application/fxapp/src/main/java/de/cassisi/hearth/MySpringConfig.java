package de.cassisi.hearth;

import de.cassisi.heart.port.ExcelReportGenerator;
import de.cassisi.hearth.port.CreateOperationJpaRepository;
import de.cassisi.hearth.port.FindAllOperationsJpaRepository;
import de.cassisi.hearth.port.FindOperationJpaRepository;
import de.cassisi.hearth.port.HLMExcelFileReader;
import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.interactor.*;
import de.cassisi.hearth.usecase.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfig {

    private final CreateOperationJpaRepository createOperationJpaRepository;
    private final AddInfusionDataRepository addInfusionDataRepository;
    private final AddNirsDataRepository addNirsDataRepository;
    private final ReadHLMDataFileRepository readHLMDataFileRepository;
    private final FindAllOperationsJpaRepository findAllOperationsJpaRepository;
    private final FindOperationJpaRepository findOperationJpaRepository;
    private final GenerateReportRepository generateReportRepository;

    private final HLMFileReader hlmFileReader = new HLMExcelFileReader();
    private final ReportFileGenerator reportFileGenerator = new ExcelReportGenerator();

    public MySpringConfig(CreateOperationJpaRepository createOperationJpaRepository, AddInfusionDataRepository addInfusionDataRepository, AddNirsDataRepository addNirsDataRepository, ReadHLMDataFileRepository readHLMDataFileRepository, FindAllOperationsJpaRepository findAllOperationsJpaRepository, FindOperationJpaRepository findOperationJpaRepository, GenerateReportRepository generateReportRepository) {
        this.createOperationJpaRepository = createOperationJpaRepository;
        this.addInfusionDataRepository = addInfusionDataRepository;
        this.addNirsDataRepository = addNirsDataRepository;
        this.readHLMDataFileRepository = readHLMDataFileRepository;
        this.findAllOperationsJpaRepository = findAllOperationsJpaRepository;
        this.findOperationJpaRepository = findOperationJpaRepository;
        this.generateReportRepository = generateReportRepository;
    }

    @Bean
    public FindAllOperations findAllOperations() {
        return new FindAllOperationsInteractor(findAllOperationsJpaRepository);
    }

    @Bean
    public FindOperation findOperation() {
        return new FindOperationInteractor(findOperationJpaRepository);
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
        return new GenerateReportInteractor(generateReportRepository, reportFileGenerator);
    }
}
