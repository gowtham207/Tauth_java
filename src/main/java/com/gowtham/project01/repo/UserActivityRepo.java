package com.gowtham.project01.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gowtham.project01.models.UserActivityModel;

public interface UserActivityRepo extends JpaRepository<UserActivityModel, Long> {

}
