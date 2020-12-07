package de.cassisi.heartcapture;

import de.cassisi.heartcapture.port.ExcelReportFileGeneratorImpl;
import de.cassisi.heartcapture.port.*;
import de.cassisi.heartcapture.usecase.*;
import de.cassisi.heartcapture.usecase.interactor.*;
import de.cassisi.heartcapture.usecase.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpringConfig {

    private final CreateOperationJpaRepository createOperationJpaRepository;
    private final AddInfusionDataRepository addInfusionDataRepository;
    private final AddNirsDataRepository addNirsDataRepository;
    private final AddAnesthesiaDataJpaRepository addAnesthesiaDataJpaRepository;
    private final ReadHLMDataFileRepository readHLMDataFileRepository;
    private final FindAllOperationsJpaRepository findAllOperationsJpaRepository;
    private final FindOperationJpaRepository findOperationJpaRepository;
    private final GenerateReportRepository generateReportRepository;
    private final FindFullOperationJpaRepository findFullOperationJpaRepository;
    private final GenerateStatisticRepository generateStatisticRepository;
    private final LockOperationRepository lockOperationRepository;
    private final EditOperationInformationRepository editOperationInformationRepository;

    private final HLMFileReader hlmFileReader = new HLMExcelFileReader();
    private final ReportFileGenerator reportFileGenerator = new ExcelReportFileGeneratorImpl();

    public MySpringConfig(CreateOperationJpaRepository createOperationJpaRepository, AddInfusionDataRepository addInfusionDataRepository, AddNirsDataRepository addNirsDataRepository, ReadHLMDataFileRepository readHLMDataFileRepository, FindAllOperationsJpaRepository findAllOperationsJpaRepository, FindOperationJpaRepository findOperationJpaRepository, GenerateReportRepository generateReportRepository, AddAnesthesiaDataJpaRepository addAnesthesiaDataJpaRepository, FindFullOperationJpaRepository findFullOperationJpaRepository, GenerateStatisticRepository generateStatisticRepository, LockOperationRepository lockOperationRepository, EditOperationInformationRepository editOperationInformationRepository) {
        this.createOperationJpaRepository = createOperationJpaRepository;
        this.addInfusionDataRepository = addInfusionDataRepository;
        this.addNirsDataRepository = addNirsDataRepository;
        this.readHLMDataFileRepository = readHLMDataFileRepository;
        this.findAllOperationsJpaRepository = findAllOperationsJpaRepository;
        this.findOperationJpaRepository = findOperationJpaRepository;
        this.generateReportRepository = generateReportRepository;
        this.addAnesthesiaDataJpaRepository = addAnesthesiaDataJpaRepository;
        this.findFullOperationJpaRepository = findFullOperationJpaRepository;
        this.generateStatisticRepository = generateStatisticRepository;
        this.lockOperationRepository = lockOperationRepository;
        this.editOperationInformationRepository = editOperationInformationRepository;
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
    public AddAnesthesiaData addAnesthesiaData() {
        return new AddAnesthesiaDataInteractor(addAnesthesiaDataJpaRepository);
    }

    @Bean
    public ReadHLMDataFile readHLMDataFile() {
        return new ReadHLMDataFileInteractor(readHLMDataFileRepository, hlmFileReader);
    }

    @Bean
    public GenerateReport generateReport() {
        return new GenerateReportInteractor(generateReportRepository, reportFileGenerator);
    }

    @Bean
    public FindFullOperation findFullOperation() {
        return new FindFullOperationInteractor(findFullOperationJpaRepository);
    }

    @Bean
    public GenerateStatistic generateStatistic() {
        return new GenerateStatisticInteractor(generateStatisticRepository);
    }

    @Bean
    public LockOperation lockOperation() {
        return new LockOperationInteractor(lockOperationRepository);
    }

    @Bean
    public EditOperationInformation editOperationInformation() {
        return new EditOperationInformationInteractor(editOperationInformationRepository);
    }
}
