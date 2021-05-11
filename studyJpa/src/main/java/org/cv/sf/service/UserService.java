package org.cv.sf.service;

import org.cv.sf.dto.entity.MUserEntity;
import org.cv.sf.repository.jpa.UserRepository;
import org.cv.sf.repository.mybatis.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public Optional<MUserEntity> findById(long id){
        return userRepository.findById(id);
    }

    public MUserEntity findLast(){
        return userRepository.findOneDesc();
    }

    public Page<MUserEntity> getPageUser(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    /**
     * 1 添加和修改 都可以使用save方法
     */
    public void save(MUserEntity user){
        userRepository.save(user);
    }

    public void deleteById(long id){
        userMapper.deleteByPrimaryKey(Integer.valueOf(id+""));
    }



    public MUserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
