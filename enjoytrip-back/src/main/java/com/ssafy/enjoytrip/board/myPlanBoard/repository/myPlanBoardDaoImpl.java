package com.ssafy.enjoytrip.board.myPlanBoard.repository;

import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanBoardDto;
import com.ssafy.enjoytrip.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class myPlanBoardDaoImpl implements myPlanBoardDao {
	
	private static myPlanBoardDao boardDao;
	private DBUtil dbUtil;
	
	private myPlanBoardDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}

	public static myPlanBoardDao getBoardDao() {
		if(boardDao == null)
			boardDao = new myPlanBoardDaoImpl();
		return boardDao;
	}

	@Override
	public void writeArticle(myPlanBoardDto boardDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into myPlanBoard (myPlanUser_id, myPlanSubject, myPlanContent) \n");
			sql.append("values (?, ?, ?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, boardDto.getUserId());
			pstmt.setString(2, boardDto.getSubject());
			pstmt.setString(3, boardDto.getContent());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public List<myPlanBoardDto> listArticle(Map<String, Object> param) throws SQLException {
		List<myPlanBoardDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select myPlanArticle_no, myPlanUser_id, myPlanSubject, myPlanContent, myPlanHit, myPlanRegister_time \n");
			sql.append("from myPlanBoard \n");
			String key = (String) param.get("key");
			String word = (String) param.get("word");
			if(!key.isEmpty() && !word.isEmpty()) {
				if("subject".equals(key)) {
					sql.append("where myPlanSubject like concat('%', ?, '%') \n");
				} else {
					sql.append("where ").append(key).append(" = ? \n");
				}
			}
			sql.append("order by myPlanArticle_no desc \n");
			sql.append("limit ?, ?");
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 0;
			if(!key.isEmpty() && !word.isEmpty())
				pstmt.setString(++idx, word);
			pstmt.setInt(++idx, (Integer) param.get("start"));
			pstmt.setInt(++idx, (Integer) param.get("listsize"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				myPlanBoardDto boardDto = new myPlanBoardDto();
				boardDto.setArticleNo(rs.getInt("myPlanArticle_No"));
				boardDto.setUserId(rs.getString("myPlanUser_id"));
				boardDto.setSubject(rs.getString("myPlanSubject"));
				boardDto.setContent(rs.getString("myPlanContent"));
				boardDto.setHit(rs.getInt("myPlanHit"));
				boardDto.setRegisterTime(rs.getString("myPlanRegister_time"));
				
				list.add(boardDto);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}
	
	@Override
	public int getTotalArticleCount(Map<String, Object> param) throws SQLException {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select count(myPlanArticle_no) \n");
			sql.append("from myPlanBoard \n");
			String key = (String) param.get("key");
			String word = (String) param.get("word");
			if(!key.isEmpty() && !word.isEmpty()) {
				if("subject".equals(key)) {
					sql.append("where myPlanSubject like concat('%', ?, '%') \n");
				} else {
					sql.append("where ").append(key).append(" = ? \n");
				}
			}
			pstmt = conn.prepareStatement(sql.toString());
			if(!key.isEmpty() && !word.isEmpty())
				pstmt.setString(1, word);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return cnt;
	}

	@Override
	public myPlanBoardDto getArticle(int articleNo) throws SQLException {
		myPlanBoardDto boardDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select myPlanArticle_no, myPlanUser_id, myPlanSubject, myPlanContent, myPlanHit, myPlanRegister_time \n");
			sql.append("from myPlanBoard \n");
			sql.append("where myPlanArticle_no = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, articleNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				boardDto = new myPlanBoardDto();
				boardDto.setArticleNo(rs.getInt("myPlanArticle_no"));
				boardDto.setUserId(rs.getString("myPlanUser_id"));
				boardDto.setSubject(rs.getString("myPlanSubject"));
				boardDto.setContent(rs.getString("myPlanContent"));
				boardDto.setHit(rs.getInt("myPlanHit"));
				boardDto.setRegisterTime(rs.getString("myPlanRegister_time"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return boardDto;
	}

	@Override
	public void updateHit(int articleNo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update myPlanBoard \n");
			sql.append("set myPlanHit = myPlanHit + 1 \n");
			sql.append("where myPlanArticle_no = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}	
	}

	@Override
	public void modifyArticle(myPlanBoardDto boardDto) throws SQLException {
		// TODO : 글번호에 해당하는 제목과 내용 변경.
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update myPlanBoard \n");
			sql.append("set myPlanSubject = ?, myPlanContent = ? \n");
			sql.append("where article_no = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, boardDto.getSubject());
			pstmt.setString(2, boardDto.getContent());
			pstmt.setInt(3, boardDto.getArticleNo());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void deleteArticle(int articleNo) throws SQLException {
		// TODO : 글번호에 해당하는 글삭제
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("delete from myPlanBoard \n");
			sql.append("where article_no = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

}
