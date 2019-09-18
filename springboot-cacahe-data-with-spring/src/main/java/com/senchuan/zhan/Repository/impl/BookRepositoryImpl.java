package com.senchuan.zhan.Repository.impl;

import com.senchuan.zhan.Repository.BookRepository;
import com.senchuan.zhan.entity.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BookRepositoryImpl implements BookRepository {

    @Override
    @Cacheable("books")
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }

    private void simulateSlowService() {
        try {
            long time = 6000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
