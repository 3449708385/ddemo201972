package com.mgp.ddemo.user.service;

import com.mgp.ddemo.user.bean.Jiepai;
import org.bson.types.ObjectId;

import java.awt.print.Book;
import java.util.List;

public interface JiepaiService {
    public String saveObj(Jiepai jp);
    public List<Jiepai> findAll();
    public Jiepai getJiepaiById(ObjectId id);
    public List<Jiepai> getJiepaiByTitle(String name);
    public String updateJiepai(Jiepai jp);
    public String deleteJiepai(Jiepai jp);
    public String deleteJiepaiById(ObjectId id);

    //mongo测试
    void mongoOperate();
}
