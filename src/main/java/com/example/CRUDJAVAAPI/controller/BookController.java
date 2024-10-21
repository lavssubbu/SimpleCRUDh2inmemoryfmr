package com.example.CRUDJAVAAPI.controller;

import com.example.CRUDJAVAAPI.model.Book;
import com.example.CRUDJAVAAPI.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getallbooks")
    public ResponseEntity<List<Book>> GetAllBooks()
    {
        try
        {
            List<Book> lstbooks = new ArrayList<>();
            bookRepo.findAll().forEach(lstbooks::add);

            if(lstbooks.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lstbooks, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getbookbyid/{id}")
    public ResponseEntity<Book> GetBookbyId(@PathVariable Long id)
    {
      Optional<Book> bukdata = bookRepo.findById(id);

      if(bukdata.isPresent())
      {
          return new ResponseEntity<>(bukdata.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> AddBook(@RequestBody Book book)
    {
      Book bukobj= bookRepo.save(book);
      return new ResponseEntity<>(bukobj,HttpStatus.OK);
    }
    @PostMapping("/Updatebyid/{id}")
    public ResponseEntity<Book> Update(@PathVariable Long id, @RequestBody Book newbook)
    {
        Optional<Book> oldbukdata = bookRepo.findById(id);

        if(oldbukdata.isPresent())
        {
            Book updatedbook = oldbukdata.get();
            updatedbook.setTitle(newbook.getTitle());
            updatedbook.setAuthor(newbook.getAuthor());

            Book bookobj = bookRepo.save(updatedbook);
            return new ResponseEntity<>(bookobj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/deletebookbyid/{id}")
    public ResponseEntity<HttpStatus> DeleteBook(@PathVariable Long id)
    {
       bookRepo.deleteById(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }

}
