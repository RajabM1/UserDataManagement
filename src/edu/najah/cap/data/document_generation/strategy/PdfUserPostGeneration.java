package edu.najah.cap.data.document_generation.strategy;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;
import edu.najah.cap.posts.PostService;

import java.io.IOException;
import java.util.List;

public class PdfUserPostGeneration implements IDocumentGeneration {
    private static final IPostService postService = new PostService();

    @Override
    public void generateDocument(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        List<Post> userPost = postService.getPosts(userName);
        String fileName = "storage\\UserPost.pdf";

        try (PdfWriter pdfWriter = new PdfWriter(fileName);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter);
             Document document = new Document(pdfDocument)) {

            document.add(new Paragraph("Post Report \n"));

            for (Post post : userPost) {
                document.add(new Paragraph("Post ID: " + post.getId()));
                document.add(new Paragraph("Title: " + post.getTitle()));
                document.add(new Paragraph("Body: " + post.getBody()));
                document.add(new Paragraph("Author: " + post.getAuthor()));
                document.add(new Paragraph("Date: " + post.getDate()));
                document.add(new Paragraph("\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
