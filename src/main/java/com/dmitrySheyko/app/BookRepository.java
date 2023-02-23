package com.dmitrySheyko.app;

import com.dmitrySheyko.app.services.ProjectRepository;
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
        logger.info("Store new book");
        repo.add(book);
    }

    // TODO: 23.02.2023 добавить проверку что в ID только цифры 
    @Override
    public void removeItemById(Integer bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("Removal successfully, deleted book id=" + bookIdToRemove);
            }
        }
        logger.info("Removal false, not found id=" + bookIdToRemove);
    }

    @Override
    public void removeByRegex(String[] searchParameters) {
        String authorForDelete = searchParameters[0];
        String titleForDelete = searchParameters[1];
        String sizeForDelete = searchParameters[2];
    }

}
