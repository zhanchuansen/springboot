package com.senchuan.zhan.Repository;

import com.senchuan.zhan.entity.Book;

import java.util.List;

public interface BookRepository {
    Book getByIsbn(String isbn);
}
