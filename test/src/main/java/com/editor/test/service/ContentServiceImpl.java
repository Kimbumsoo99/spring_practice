package com.editor.test.service;

import com.editor.test.dto.SaveDto;
import com.editor.test.entity.ContentEntity;
import com.editor.test.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService{

    private ContentRepository contentRepository;

    @Autowired
    ContentServiceImpl(ContentRepository contentRepository){
        this.contentRepository = contentRepository;
    }


    @Override
    public void saveContent(SaveDto saveDto) {
        String title = saveDto.getTitle();
        String content = saveDto.getContent();

        ContentEntity content1 = new ContentEntity();

        // setter의 사용은 지양해야 한다. set 메서드가 아닌, 생성자로 넣어줘야 함.
        // 실무에서는 그렇게 해야한다는 것만 알자.
        content1.setTitle(title);
        content1.setContent(content);

        // INSERT INTO
        contentRepository.save(content1);
    }
}
