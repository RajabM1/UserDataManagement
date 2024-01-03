package edu.najah.cap.data.data_exporting.template;

import edu.najah.cap.data.data_exporting.compress.Compressor;
import edu.najah.cap.data.data_exporting.compress.ZipCompressedStrategy;
import edu.najah.cap.data.data_exporting.document_generation.DocumentGenerationFactory;
import edu.najah.cap.data.data_exporting.document_generation.Generator;
import edu.najah.cap.data.data_exporting.document_generation.strategy.AbstractPdfDocumentGeneration;
import edu.najah.cap.data.data_exporting.helpers.GetPathsHelper;
import edu.najah.cap.data.exceptions.DocumentGenerationException;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.iam.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public abstract class DataExportingTemplate {
    protected static final Logger logger = LoggerFactory.getLogger(DataExportingTemplate.class);
    private static final IUserService userService = new UserService();

    protected final String compressedFilePath = "storage/data.zip";
    protected List<String> fileList;
    protected Compressor compressor;

    /**
     * Initiates the data exporting process, including PDF generation, compression, and uploading.
     *
     * @param userName The username for which data is to be exported.
     */
    public final void export(String userName) throws NotFoundException, DocumentGenerationException {
        try {
            generatePdf(userName);
            compressPdf();
            upload();
        }catch (DocumentGenerationException e){
            logger.error("Error in document generation for user: {}. Halting export process.", userName, e);
            throw e;
        }

    }

    /**
     * Generates a PDF document for a user.
     *
     * @param userName the username associated with the user to generate the PDF for.
     * @throws NotFoundException if the user data is not found in the system after retries.
     */
    protected void generatePdf(String userName) throws NotFoundException , DocumentGenerationException {
        UserType userType = null;
        boolean success = false;
        int retryCount = 0;
        int maxRetries = 3;

        while (!success && retryCount < maxRetries) {
            try {
                userType = userService.getUser(userName).getUserType();
                success = true;
            } catch (SystemBusyException systemBusyException) {
                logger.error("System busy while get user data: " + userName, systemBusyException.getMessage());
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (BadRequestException badRequestException) {
                logger.error("Bad request in get user service: " + userName, badRequestException.getMessage());
                break;
            } catch (NotFoundException notFoundException) {
                logger.error("Required resource not found in get user service: " + userName, notFoundException.getMessage());
                break;
            } catch (Exception exception) {
                logger.error("Unexpected error in get user data: " + userName, exception.getMessage());
                break;
            }
        }
        if (success) {
            Generator generator = DocumentGenerationFactory.create(userType, userName);
            generator.generateUserActivity();
            generator.generateUserData();
            generator.generateUserPost();
            generator.generateUserPayment();
        }else {
            throw new NotFoundException("User Not Found in DB");
        }
    }

    /**
     * Compresses generated PDF files into a ZIP archive.
     * The compression strategy is set to ZipCompressedStrategy.
     */
    protected void compressPdf() {
        this.compressor = Compressor.getCompressorInstance();
        this.compressor.setCompressStrategy(new ZipCompressedStrategy());
        this.fileList = GetPathsHelper.getFilesPathsList(".pdf");
        try {
            logger.info("Start compressing data.");
            compressor.compress(compressedFilePath, fileList);
            logger.info("Data compressing successfully.");
        } catch (IOException ioException) {
            logger.error("Error while trying to compressing data. " + ioException.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error while trying to compressing data. " + exception.getMessage());
        }
    }

    /**
     * Abstract method that concrete subclasses must implement for uploading the compressed data.
     */
    protected abstract void upload();

}
