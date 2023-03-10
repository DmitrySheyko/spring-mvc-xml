package com.dmitrySheyko.app.services;

import com.dmitrySheyko.app.repositories.BookRepository;
import com.dmitrySheyko.app.repositories.ProjectRepository;
import com.dmitrySheyko.web.dto.Book;
import com.dmitrySheyko.web.dto.BookIdToRemove;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void save(Book book) {
        logger.info("Attempt of saving book in bookService");
        if (isAuthorCorrect(book.getAuthor()) || isTitleCorrect(book.getTitle()) || isSizeCorrect(book.getSize())) {
            bookRepo.store(book);
        } else {
            logger.info("Book hasn't saved. Book description is incorrect");
        }
    }

    public void removeBookId(String bookIdToRemove) {
        logger.info("Attempt of removing book by id in bookService");
        if (isBookIdCorrect(bookIdToRemove)) {
            bookRepo.removeItemById(bookIdToRemove);
        } else {
            logger.info("Book hasn't removed. Book id is incorrect");
        }
    }

    public void removeBooksByRegex(String regex) {
        logger.info("Attempt of removing book by regex in bookService");
        if (isRegexCorrect(regex)) {
            String[] searchParameters = regex.split("/");
            String authorForDelete = searchParameters[0];
            String titleForDelete = searchParameters[1];
            String sizeForDelete = searchParameters[2];
            bookRepo.removeByRegex(authorForDelete, titleForDelete, sizeForDelete);
        } else {
            logger.info("Books hasn't removed. Regex is incorrect");
        }
    }

    private boolean isAuthorCorrect(String author) {
        return !StringUtils.isEmptyOrWhitespace(author);
    }

    private boolean isTitleCorrect(String title) {
        return !StringUtils.isEmptyOrWhitespace(title);
    }

    private boolean isSizeCorrect(Integer size) {
        return size != null && size >= 0;
    }

    private boolean isBookIdCorrect(String bookId) {
        return (bookId != null);
    }

    private boolean isRegexCorrect(String regex) {
        String[] searchParameters = regex.split("/");
        if (searchParameters.length != 3) return false;
        return !StringUtils.isEmptyOrWhitespace(searchParameters[0]) &&
                !StringUtils.isEmptyOrWhitespace(searchParameters[1]) &&
                !StringUtils.isEmptyOrWhitespace(searchParameters[2]);
    }

    private void defaultInit() {
        logger.info("default INIT in bookService");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in bookService");
    }

}