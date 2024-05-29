package com.editor.test.service;

import com.editor.test.dto.SaveDto;
import com.editor.test.entity.ContentEntity;
import com.editor.test.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // 참고로 이미 존재하는 PK에 대해서 save를 진해아면, UPDATE SET이 동작한다. -> JPA의 특성
        contentRepository.save(content1);
    }

    @Override
    public ContentEntity selectOneContent(String id) {
        int to = Integer.parseInt(id);

        return contentRepository.findById(to);
    }

    @Override
    public void updateOneContent(SaveDto saveDto, String id) {
        int to = Integer.parseInt(id);
        ContentEntity content1 = new ContentEntity();
        content1.setId(to);
        content1.setTitle(saveDto.getTitle());
        content1.setContent(saveDto.getContent());

        contentRepository.save(content1);
    }

    @Override
    public List<ContentEntity> selectContent() {
        return contentRepository.findAll();
    }

    @Override
    public void deleteOneContent(String id) {
        int to = Integer.parseInt(id);
        contentRepository.deleteById(to);
    }
}
