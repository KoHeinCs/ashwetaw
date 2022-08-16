package com.ashwetaw.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Cache like that
 *  User    Attempt
 *  user1   3
 *  user2   2
 *  user3   5
 *  user4   7 (  this user will be locked coz exceeded maximum of attempts)
 */

@Service
public class LoginAttemptService {
    public static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    public static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String,Integer> loadingAttemptCache;

    public LoginAttemptService(){
        super();
        loadingAttemptCache = CacheBuilder.newBuilder()
                                .expireAfterAccess(15, TimeUnit.MINUTES)
                                .maximumSize(100) // for no: of users 100
                                .build(new CacheLoader<String, Integer>() {
                                    @Override
                                    public Integer load(String key) throws Exception {
                                        return 0;
                                    }
                                });
    }

    public void removeUserFromLoginAttemptCache(String username){
        loadingAttemptCache.invalidate(username);
    }

    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT  + loadingAttemptCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loadingAttemptCache.put(username,attempts);
    }

    public boolean hasExceededMaxAttempts(String username) {
        boolean result = false;
        try {
            result =  loadingAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }


}
