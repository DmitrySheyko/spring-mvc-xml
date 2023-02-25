package com.dmitrySheyko.web.controllers;

import com.dmitrySheyko.app.services.BookService;
import com.dmitrySheyko.web.dto.Book;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("Request for show book_shelf page");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.save(book);
        logger.info("Book saved. Current repository size=" + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") String bookIdToRemove) {
        bookService.removeBookId(bookIdToRemove);
        logger.info("Book deleted by Id. Current repository size=" + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeByRegex")
    public String removeBookByRegex (@RequestParam(value = "queryRegex") String regex) {
        bookService.removeBooksByRegex(regex);
        logger.info("Book deleted by regex Current repository size=" + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

}
