package com.hbsi.user.service;

import com.hbsi.domain.User;

public interface UserAuth{
	boolean isAdmin(User user);
}
