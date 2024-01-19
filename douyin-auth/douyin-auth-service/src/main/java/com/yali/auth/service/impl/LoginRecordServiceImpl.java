package com.yali.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.auth.domain.po.LoginRecord;
import com.yali.auth.mapper.LoginRecordMapper;
import com.yali.auth.service.ILoginRecordService;
import com.yali.common.utils.MarkedRunnable;
import com.yali.common.utils.StringUtils;
import com.yali.common.utils.WebUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 登录信息记录表 服务实现类
 * </p>
 */
@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord> implements ILoginRecordService {

    private static final Executor WRITE_RECORD_EXECUTOR;

    static {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(20);
        //配置最大线程数
        executor.setMaxPoolSize(40);
        //配置队列大小
        executor.setQueueCapacity(99999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("login-record-write-worker-");
        // 设置拒绝策略：放弃任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //执行初始化
        executor.initialize();
        WRITE_RECORD_EXECUTOR = executor;
    }

    @Override
    public void saveAsync(LoginRecord record) {
        WRITE_RECORD_EXECUTOR.execute(new MarkedRunnable(() -> save(record)));
    }

    @Override
    public void updateLogoutTimeAsync(LoginRecord record) {
        WRITE_RECORD_EXECUTOR.execute(new MarkedRunnable(() -> updateById(record)));

    }


    @Override
    public void loginSuccess(String cellphone, Long userId, Duration expireTime, Integer status) {
        // 获取用户代理信息，包含设备和浏览器信息
        String userAgent = WebUtils.getUserAgent();
        if (!StringUtils.isEmpty(userAgent)) {
            System.out.println("userAgent = " + userAgent);
        }

        LoginRecord record = new LoginRecord();
        LocalDateTime now = LocalDateTime.now();
        //记录登录耗时
        record.setStatus(status);
        record.setDuration(expireTime.toMillis());
        record.setLoginTime(now);
        record.setLoginDate(now.toLocalDate());
        record.setUserId(userId);
        record.setCellPhone(cellphone);
        record.setIpv4(WebUtils.getRemoteAddr());
        saveAsync(record);
    }

    @Override
    public void loginFail(String cellphone, Long userId, Duration expireTime, Integer status, String message) {
        LoginRecord record = new LoginRecord();
        LocalDateTime now = LocalDateTime.now();
        //记录登录耗时
        record.setFailureReason(message);
        record.setStatus(status);
        record.setDuration(expireTime.toMillis());
        record.setLoginTime(now);
        record.setLoginDate(now.toLocalDate());
        record.setUserId(userId);
        record.setCellPhone(cellphone);
        record.setIpv4(WebUtils.getRemoteAddr());
        saveAsync(record);
    }

    @Override
    public void logout(Long userId) {
        //1.查询当前用户最近一次登录记录,并设置退出时间
        LoginRecord record = lambdaQuery().eq(LoginRecord::getUserId, userId)
                .orderByDesc(LoginRecord::getLoginTime)
                .last("limit 1")
                .one();
        LocalDateTime now = LocalDateTime.now();
        //1.设置当前用户退出时间
        record.setLogoutTime(now);
        updateLogoutTimeAsync(record);
    }
}
