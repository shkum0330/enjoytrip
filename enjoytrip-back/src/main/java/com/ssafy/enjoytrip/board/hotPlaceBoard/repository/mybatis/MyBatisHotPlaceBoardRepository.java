package com.ssafy.enjoytrip.board.hotPlaceBoard.repository.mybatis;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceFileInfoDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.repository.HotPlaceBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisHotPlaceBoardRepository implements HotPlaceBoardRepository {
    private final HotPlaceBoardMapper hotPlaceBoardMapper;
    @Override
    public void writeArticle(HotPlaceBoardDto boardDto) throws SQLException {
        hotPlaceBoardMapper.writeArticle(boardDto);
    }

    @Override
    public void registerFile(HotPlaceBoardDto boardDto) throws Exception {
        hotPlaceBoardMapper.registerFile(boardDto);
    }

    @Override
    public Optional<HotPlaceBoardDto> findById(int articleNo) throws SQLException {
        return hotPlaceBoardMapper.findById(articleNo);
    }

    @Override
    public List<HotPlaceBoardDto> findBySubject(String subject) throws SQLException {
        return hotPlaceBoardMapper.findBySubject(subject);
    }

    @Override
    public List<HotPlaceBoardDto> findByContent(String content) throws SQLException {
        return hotPlaceBoardMapper.findByContent(content);
    }

    @Override
    public List<HotPlaceBoardDto> findByWriter(String userName) throws SQLException {
        return hotPlaceBoardMapper.findByWriter(userName);
    }

    //    @Override
//    public List<HotPlaceBoardDto> listArticle(Map<String, Object> param) throws SQLException {
//        return hotPlaceBoardMapper.listArticle(param);
//    }
@Override
public List<HotPlaceBoardDto> listArticle() throws SQLException {
    return hotPlaceBoardMapper.listArticle();
}
    @Override
    public int getTotalArticleCount(Map<String, Object> param) throws SQLException {
        return hotPlaceBoardMapper.getTotalArticleCount(param);
    }

    @Override
    public HotPlaceBoardDto getArticle(int articleNo) throws SQLException {
        return hotPlaceBoardMapper.getArticle(articleNo);
    }

    @Override
    public void likeArticle(int articleNo) throws SQLException {
        hotPlaceBoardMapper.likeArticle(articleNo);
    }

    @Override
    public void unlikeArticle(int articleNo) throws SQLException {
        hotPlaceBoardMapper.unlikeArticle(articleNo);
    }

    @Override
    public void updateHit(int articleNo) throws SQLException {
        hotPlaceBoardMapper.updateHit(articleNo);
    }

    @Override
    public void modifyArticle(HotPlaceBoardDto boardDto) throws SQLException {
        hotPlaceBoardMapper.modifyArticle(boardDto);
    }

    @Override
    public void deleteFile(int articleNo) throws Exception {
        hotPlaceBoardMapper.deleteFile(articleNo);
    }

    @Override
    public void deleteArticle(int articleNo) throws SQLException {
        hotPlaceBoardMapper.deleteArticle(articleNo);
    }

    @Override
    public List<HotPlaceFileInfoDto> fileInfoList(int articleNo) throws Exception {
        return hotPlaceBoardMapper.fileInfoList(articleNo);
    }
}
