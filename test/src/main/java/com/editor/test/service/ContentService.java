package com.editor.test.service;

import com.editor.test.dto.SaveDto;
import com.editor.test.entity.ContentEntity;

import java.util.List;

public interface ContentService {
    void saveContent(SaveDto saveDto);
    List<ContentEntity> selectContent();
}
