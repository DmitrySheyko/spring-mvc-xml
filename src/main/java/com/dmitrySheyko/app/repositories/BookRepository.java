package com.dmitrySheyko.app.repositories;

import com.dmitrySheyko.web.dto.Book;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository<T> implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return repo;
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("In bookRepository successfully saved book id=" + book.getId());
        repo.add(book);
    }

    @Override
    public void removeItemById(Integer bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("In bookRepository successfully deleted book id=" + bookIdToRemove);
            }
        }
        logger.info("In bookRepository false deletion of book id=" + bookIdToRemove);
    }

    @Override
    public void removeByRegex(String authorForDelete, String titleForDelete, String sizeForDelete) {
        List<Book> listForDelete = new ArrayList<>();
        int size;
        for (Book book : repo) {
            boolean shouldBeDeletedByAuthor = authorForDelete.equals("*") || authorForDelete.equals(book.getAuthor());
            boolean shouldBeDeletedByTitle = titleForDelete.equals("*") || titleForDelete.equals(book.getTitle());
            boolean shouldBeDeletedBySize;
            if (sizeForDelete.equals("*")) {
                shouldBeDeletedBySize = true;
            } else if (sizeForDelete.charAt(0) == '>') {
                size = Integer.parseInt(sizeForDelete.substring(1));
                shouldBeDeletedBySize = book.getSize() > size;
            } else if (sizeForDelete.charAt(0) == '<') {
                size = Integer.parseInt(sizeForDelete.substring(1));
                shouldBeDeletedBySize = book.getSize() < size;
            } else {
                size = Integer.parseInt(sizeForDelete);
                shouldBeDeletedBySize = size == book.getSize();
            }
            if (shouldBeDeletedByAuthor && shouldBeDeletedByTitle && shouldBeDeletedBySize) {
                listForDelete.add(book);
            }
        }
        repo.removeAll(listForDelete);
        logger.info("In bookRepository successfully completed deletion by regex=");
    }

}
