package com.service.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.customer.entity.UserInfo;

/**
 * The Interface UserInfoRepository.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	/**
	 * Find by name.
	 *
	 * @param username the username
	 * @return the optional
	 */
	Optional<UserInfo> findByName(String username);

}
