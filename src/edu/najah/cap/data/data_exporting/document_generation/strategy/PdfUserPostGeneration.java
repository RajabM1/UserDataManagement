package edu.najah.cap.data.data_exporting.document_generation.strategy;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;
import edu.najah.cap.posts.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PdfUserPostGeneration extends AbstractPdfDocumentGeneration {
    private static final Logger logger = LoggerFactory.getLogger(PdfUserPostGeneration.class);

    private static final IPostService postService = new PostService();

    /**
     * Adds user post content to the PDF document.
     *
     * @param document The document to which user activity content is added.
     * @param userName The username for whom the activity report is being generated.
     * @throws SystemBusyException If the system is busy and cannot add content.
     * @throws BadRequestException If the request parameters are invalid.
     * @throws NotFoundException   If the user is not found.
     */
    @Override
    protected void addContent(Document document, String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        logger.info("Starting to add user post content for {}", userName);

        List<Post> userPost = postService.getPosts(userName);
        document.add(new Paragraph("Post Report \n"));

        for (Post post : userPost) {
            document.add(new Paragraph("Post ID: " + post.getId()));
            document.add(new Paragraph("Title: " + post.getTitle()));
            document.add(new Paragraph("Body: " + post.getBody()));
            document.add(new Paragraph("Author: " + post.getAuthor()));
            document.add(new Paragraph("Date: " + post.getDate()));
            document.add(new Paragraph("\n"));
        }
        logger.info("User post content added successfully for {}", userName);

    }

    /**
     * Provides the file name for the user post PDF document.
     *
     * @return The file name for the generated user activity PDF document.
     */
    @Override
    protected String getFileName() {
        return "storage\\UserPost.pdf";
    }
}
