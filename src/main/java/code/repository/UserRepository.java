package code.repository;

import code.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllByUserId(Integer userId);

    //    UserEntity findByPhoneNumberAndPassword(String phoneNumber, String password);
    UserEntity findUserByUserId(Integer userId);

    UserEntity findUserByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE user_id=?1 ")
    UserEntity findByUserId(Integer userId);

    @Query(nativeQuery = true, value = "SELECT user_id,user_email FROM user WHERE user_phone = :phoneNumber or user_email = :email ;")
    String findByPhoneNumberOrEmailParam(@Param(value = "phoneNumber") String phoneNumber,
                                         @Param(value = "email") String email);

    @Query(nativeQuery = true, value = "SELECT user_id,user_email FROM user WHERE user_phone = ?1 and user_password = ?2 ;")
    String findUserByPhoneNumberAndPassword(String phoneNumber, String password);

    UserEntity findUserByName(String name);

    //câu query update luôn
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "UPDATE user SET user_password = ?1 WHERE user_id = ?2")
//    int updateUsingNativeModify(String password, Integer userId);

}

