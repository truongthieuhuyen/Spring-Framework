package code.repository;

import code.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllByUserId(Integer userId);
    List<UserEntity> findAllByPhoneNumberAndPassword(String phoneNumber, String password);

    //    UserEntity findByPhoneNumberAndPassword(String phoneNumber, String password);
    UserEntity findUserByUserId(Integer userId);

    @Query (nativeQuery = true, value = "SELECT user_phone,user_email FROM user WHERE user_phone = '?1' ;")
    UserEntity findUserByUserPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "SELECT user_phone,user_email FROM user WHERE user_phone = '?1' ;")
    UserEntity findUserByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "SELECT user_phone,user_email FROM user WHERE user_phone = '?1' and user_password = '?2';")
    UserEntity findUserByPhoneNumberAndPassword( String phoneNumber, String password);

}

