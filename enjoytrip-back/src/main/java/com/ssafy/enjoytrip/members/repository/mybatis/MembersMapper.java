package com.ssafy.enjoytrip.members.repository.mybatis;

import com.ssafy.enjoytrip.members.model.MembersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MembersMapper {
    void join(MembersDto dto);
    MembersDto login(MembersDto dto);
    List<MembersDto> listMembers();
    Optional<MembersDto> findById(@Param("userId") String userId);
    void updateMember(@Param("userId") String userId, @Param("updateParam") MembersDto dto);
    void deleteMember(String userId);
}
