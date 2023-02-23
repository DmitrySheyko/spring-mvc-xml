package com.dmitrySheyko.app;

import com.dmitrySheyko.app.services.ProjectRepository;
import com.dmitrySheyko.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static org.thymeleaf.util.StringUtils.*;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void save(Book book) {
        if (isAuthorCorrect(book.getAuthor()) || isTitleCorrect(book.getTitle()) || isSizeCorrect(book.getSize())) {
            bookRepo.store(book);
        }
    }

    public void removeBookId(Integer bookIdToRemove) {
        bookRepo.removeItemById(bookIdToRemove);
    }

    private boolean isAuthorCorrect(String author) {
        return !StringUtils.isEmptyOrWhitespace(author);
    }

    private boolean isTitleCorrect(String title) {
        return !StringUtils.isEmptyOrWhitespace(title);
    }

    private boolean isSizeCorrect(Integer size) {
        if (size == null) {
            return false;
        }
        if (size < 0) {
            return false;
        }
        return true;
    }

    public void removeBookByRegex(String regex) {
        String[] searchParameters = regex.split("/");
        bookRepo.removeByRegex(searchParameters);

    }

    private boolean isRegexCorrect(String regex) {
        String[] searchParameters = regex.split("/");
        if (searchParameters.length != 3) return false;
        return !StringUtils.isEmptyOrWhitespace(searchParameters[0]) &&
                !StringUtils.isEmptyOrWhitespace(searchParameters[1]) &&
                !StringUtils.isEmptyOrWhitespace(searchParameters[2]);
    }
}