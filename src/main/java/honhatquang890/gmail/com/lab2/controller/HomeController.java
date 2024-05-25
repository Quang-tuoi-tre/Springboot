package honhatquang890.gmail.com.lab2.controller;

import honhatquang890.gmail.com.lab2.model.Book;
import honhatquang890.gmail.com.lab2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller public class HomeController {
    @Autowired
    private BookService bookService;
    @GetMapping("/book")
    public String hello(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "Book/index";
    }
    @GetMapping("/book/create")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "Book/Create";
    }

    @GetMapping("/book/{id}")
    public String getBookDetail(@PathVariable int id, Model model) {
        bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
        return "Book/subpage";
    }
    @PostMapping("/book/create")
    public String createBook(Book book, Model model) {
        // Add the new book to the list or save it to your database
        // For simplicity, let's assume adding to the list using BookService
        bookService.add(book);
        // Redirect to the book list page
        return "redirect:/book";
    }


}
