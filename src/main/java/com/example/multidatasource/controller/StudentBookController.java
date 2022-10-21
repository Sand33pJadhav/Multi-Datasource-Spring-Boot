package com.example.multidatasource.controller;

import com.example.multidatasource.book.entity.Book;
import com.example.multidatasource.book.repository.BookRepository;
import com.example.multidatasource.student.entity.Student;
import com.example.multidatasource.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class StudentBookController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/Student")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/Book")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @PostMapping("/Student/add")
    public Student addStudent(@RequestBody Student student){
        return studentRepository.save(student);
    }

    @PostMapping("/Book/add")
    public Book addBook(@RequestBody Book book){
        return bookRepository.save(book);
    }
}
