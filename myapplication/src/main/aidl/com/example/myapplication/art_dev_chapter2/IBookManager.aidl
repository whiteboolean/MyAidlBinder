// IBookManager.aidl
package com.example.myapplication.art_dev_chapter2;
import com.example.myapplication.art_dev_chapter2.Book;

// Declare any non-default types here with import statements
//会在 build/generated/aidl_source_output_dir/debug/out/com.example.myapplication.art_dev_chapter2目录下面生成一个接口
interface IBookManager {
    List<Book> getBook(); //从远端获取图书
    void addBook(in Book book); //从远端添加图书
}